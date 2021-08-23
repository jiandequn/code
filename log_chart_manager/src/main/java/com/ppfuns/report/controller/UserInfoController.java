package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountWeek;
import com.ppfuns.report.entity.UserInfo;
import com.ppfuns.report.entity.base.VennEntity;
import com.ppfuns.report.service.IUserInfoService;
import com.ppfuns.report.utils.DateUtils;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.NumberFormat;
import java.util.*;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
@RestController
@RequestMapping("/report/user-info")
public class UserInfoController {
    @Autowired
    private IUserInfoService iUserInfoService;

    /**
     * 区域新增用户-饼图
     * @return
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        ModelAndView mav = new ModelAndView("/report/user/show");
        return mav;
    }

    /**
     * 区域新增用户-饼图
     * @return
     */
    @RequestMapping(value = "/area_increase_chart_ratio", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        ModelAndView mav = new ModelAndView("/report/user/area_increase_user_count_ratio");
        return mav;
    }

    /**
     * 周新增用户数-柱图页
     * @return
     */
    @RequestMapping(value = "/increase_user_count_week", method = RequestMethod.GET)
    public ModelAndView increasUserWeek() {
        ModelAndView mav = new ModelAndView("/report/app/increase_user_count_week");
        return mav;
    }

    /**
     *专区累计访问用户--韦恩图
     * @return
     */
    @RequestMapping(value = "/increase_user_count_venn", method = RequestMethod.GET)
    public ModelAndView increasUserVenn() {
        ModelAndView mav = new ModelAndView("/report/user/increase_user_count_venn");
        return mav;
    }
    /**
     * 获取区域新增用户占比
     * @param userType
     * @param parentColumnId
     * @return
     */
    @RequestMapping("/area/chart_ratio/increase_list")
    public List<Map<String,Object>> selectAreaIncreaseUserCount(String userType,String parentColumnId,String startDate ,String endDate) {
        List<Map<String,Object>> list = new ArrayList<>();
        QueryWrapper qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("create_time",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("create_time",endDate));
        List<AppAreaVisitCountWeek> userList =this.iUserInfoService.selectAreaIncreaseUserCount(qw);
        Long count = userList.stream().mapToLong(AppAreaVisitCountWeek::getPageUserCount).sum();
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(4);
        userList.forEach(s->{
            Map<String,Object> map = new HashMap<>();
            String result = numberFormat.format((double)  s.getPageUserCount()/ (double)count* 100);//所占百分比
            if(StringUtils.isEmpty(s.getAreaCode())){
                map.put("name","未知");
            }else{
                map.put("name",s.getAreaCode()+"-"+s.getAreaName());
            }
            map.put("y",Double.parseDouble(result));
            list.add(map);
        });
        return list;
    }
    @RequestMapping("/get/increase_user_count/chart")
    public Map<String,Object> getIncreaseUserCountChartByWeek(String parentColumnId,String startDate ,String endDate) {
        QueryWrapper qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("create_time",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("create_time",endDate));
        List<AppAreaVisitCountWeek> userList =this.iUserInfoService.getIncreaseUserCountChartByWeek(qw);
        List categories = new ArrayList<>(userList.size());
        List data = new ArrayList<>(userList.size());
        userList.forEach(s->{
            categories.add(s.getY()+"年"+s.getW()+"周");
            data.add(s.getPageUserCount());
        });
        Map<String,Object> serieMap = new HashMap<>();
        serieMap.put("name","新增用户数");
        serieMap.put("data",data);
        List series = new ArrayList<>();
        series.add(serieMap);
        Map<String,Object> result = new HashMap<>();
        result.put("series",series);
        result.put("categories",categories);
        return result;
    }
    @RequestMapping("/get/increase_user_count/venn")
    public List<VennEntity> getIncreaseUserCountVenn(@RequestParam(defaultValue = "vod") String userType) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("user_type",userType);
        qw.groupBy("parent_column_id","user_type");
        List<VennEntity> userList =this.iUserInfoService.getIncreaseUserCountVenn(qw);
        qw.clear();
        qw.eq("user_type",userType);
        List<VennEntity> userList2 =this.iUserInfoService.getIncreaseUserCountVenn2(qw);
        userList.addAll(userList2);
        return userList;
    }
    @RequestMapping("list")
    public ResultPage getList(String parentColumnId, String userType, String userId, String areaCodes, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<UserInfo> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(userId).filter(s -> !s.isEmpty()).ifPresent(t->qw.like("user_id",userId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("create_time",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("create_time",endDate));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->qw.in("area_code",areaCodes.split(",")));
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iUserInfoService.list2(qw);
            return new ResultPage(list.size(), list);
        }
        Page<UserInfo> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iUserInfoService.page2(pg,qw));
    }
    @RequestMapping("noapp_area_user_count/list")
    public ResultPage noapp_area_user_countList(@RequestParam(defaultValue="day") String dateType,String areaCodes,String parentColumnId,String userType, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        Map<String,String> params = new HashMap();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->params.put("parentColumnId",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->params.put("userType",userType));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->params.put("startDate",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->params.put("endDate",endDate));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->params.put("areaCodes",areaCodes));
        Optional.ofNullable(orderby).filter(s -> !s.isEmpty()).ifPresent(t->params.put("orderby",QueryConditionsUtils.formatOrderBy(null,orderby,null)));
        if(page ==null || limit == null){
            List list = iUserInfoService.noAppAreaUserCountList(params);
            return new ResultPage(list.size(), list);
        }
        Page<HashMap> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iUserInfoService.noAppAreaUserCountPage(pg,params));
    }
    @RequestMapping("app_area_user_count/list")
    public ResultPage app_area_user_countList(@RequestParam(defaultValue="day") String dateType,String areaCodes,String parentColumnId,String userType, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<HashMap> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->qw.in("area_code",areaCodes.split(",")));
        switch (dateType){
            case "week":   qw.groupBy("y","w","parent_column_id","user_type","area_code");break;
            case "month":   qw.groupBy("y","m","parent_column_id","user_type","area_code");break;
            case "year":   qw.groupBy("y","parent_column_id","user_type","area_code");break;
            case "quarter":   qw.groupBy("y","q","parent_column_id","user_type","area_code");break;
            default:qw.groupBy("t_date","parent_column_id","user_type","area_code");break;
        }
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iUserInfoService.areaUserCountList(qw,dateType);
            return new ResultPage(list.size(), list);
        }
        Page<HashMap> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iUserInfoService.areaUserCountPage(pg,qw,dateType));
    }

    /**
     * 专区累计访问用户数
     * @param areaCodes
     * @param parentColumnIds
     * @param userType
     * @param endDate 截至日期
     * @param page
     * @param limit
     * @param orderby
     * @return
     */
    @RequestMapping("app_total_user_count/list")
    public ResultPage app_total_user_countList(String areaCodes,String parentColumnIds,String userType, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper qw = new QueryWrapper();
        Optional.ofNullable(parentColumnIds).filter(s -> !s.isEmpty()).ifPresent(t->qw.in("parent_column_id",parentColumnIds.split(",")));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("create_time",endDate));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->qw.in("area_code",areaCodes.split(",")));
        QueryWrapper qw1 = (QueryWrapper) qw.clone();
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        String tDate=StringUtils.isEmpty(endDate)? DateUtils.getYesterday():endDate;
        qw.select(String.format("\"%s\" as create_time",tDate),"user_type","parent_column_id","count(distinct user_id) as user_id");
        qw.groupBy("user_type","parent_column_id");
        ResultPage resultPage = null;
        if(page ==null || limit == null){
            List list = iUserInfoService.list(qw);
            resultPage=new ResultPage(list.size(), list);
        }
        Page<UserInfo> pg =new Page(page,limit,true);
        resultPage = ResultPage.fromMybatisPlusPage(iUserInfoService.page(pg,qw));
        //查询统计的
        qw1.select(String.format("\"%s\" as create_time",tDate),"user_type","count(distinct user_id) as user_id");
        qw1.groupBy("user_type");
        UserInfo one = iUserInfoService.getOne(qw1);
        resultPage.setTotalRow(one);
        return resultPage;
    }
}
