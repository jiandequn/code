package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountDay;
import com.ppfuns.report.entity.AppAreaVisitCountWeek;
import com.ppfuns.report.entity.base.CountBean;
import com.ppfuns.report.service.IAppAreaVisitCountDayService;
import com.ppfuns.report.utils.ChartUtils;
import com.ppfuns.report.utils.DateUtils;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultData;
import com.ppfuns.util.ResultPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * <p>
 * 专区按天统计信息 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/report/app-area-visit-count-day")
public class AppAreaVisitCountDayController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppAreaVisitCountDayController.class);
    @Autowired
    private IAppAreaVisitCountDayService iAppAreaVisitCountDayService;
    /**
     * 日访问数据页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/visit_count_day");
        return mav;
    }
    @RequestMapping(value = "/toPage2", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("区域访问页！");
        ModelAndView mav = new ModelAndView("/report/app/area_visit_count/day/show");
        return mav;
    }
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List list() {
        List list= this.iAppAreaVisitCountDayService.list();
        return list;
    }
    @RequestMapping("noArea/list")
    public ResultPage getNoAreaList(String userType,String parentColumnId, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppAreaVisitCountDay> qw = new QueryWrapper();
        qw.select("t_date","parent_column_id","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        qw.groupBy("t_date","parent_column_id","user_type");
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iAppAreaVisitCountDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAreaVisitCountDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppAreaVisitCountDayService.page(pg,qw));
    }
    @RequestMapping("area/list")
    public ResultPage getAreaList(String userType,String parentColumnId, String startDate, String endDate,String areaCodes, Integer page, Integer limit,String orderby) {
        QueryWrapper<AppAreaVisitCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->{
            qw.in("area_code",areaCodes.split(","));
        });
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iAppAreaVisitCountDayService.areaList(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAreaVisitCountDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppAreaVisitCountDayService.areaPage(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType,String parentColumnId, String startDate, String endDate,  String countFields,String countFieldTypes) {
        QueryWrapper<AppAreaVisitCountDay> qw = new QueryWrapper();
        qw.select("t_date","parent_column_id","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        qw.groupBy("t_date","parent_column_id","user_type");
        List<AppAreaVisitCountDay> userList =this.iAppAreaVisitCountDayService.list(qw);
        if(StringUtils.contains(countFields,"perUserPlayCount")){
            userList.forEach(s->{
                if(s.getPlayUserCount()>0) {
                    s.setPerUserPlayCount(BigDecimal.valueOf(s.getPlayCount()).divide(BigDecimal.valueOf(s.getPlayUserCount()), 2, 1));
                }
            });
        }
        return ChartUtils.getChart(userList,Arrays.asList("tDate"),Arrays.asList(countFields.split(",")),Arrays.asList(countFieldTypes.split(",")));
    }

    /**
     * 周点播用户数柱状图并与上周对比，示例如下：（本周－上周）÷上周×100%
     * @param userType
     * @param parentColumnId
     * @param localWeek
     * @param countFields
     * @param countFieldTypes
     * @return
     */
    @RequestMapping("/chart/list2")
    public Map<String,Object> getChartListByWeek(String userType,String parentColumnId,String localWeek, String countFields,String countFieldTypes) {
        QueryWrapper<AppAreaVisitCountDay> qw = new QueryWrapper();
        qw.select("WEEKDAY(t_date) as t_date","parent_column_id","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        qw.groupBy("t_date","parent_column_id","user_type");
        if(StringUtils.isEmpty(localWeek)){
            int[] weekAndYear = DateUtils.getWeekAndYear(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).minusWeeks(1).toString());
            localWeek= weekAndYear[0]+"-"+weekAndYear[1];
        }
        LocalDate localMonday = DateUtils.getMondayByWEEKOFYEAR(localWeek);
        LocalDate localSunday = localMonday.plusDays(6);
        LocalDate beforeMonday = localMonday.minusWeeks(1);
        LocalDate beforeSunday = localSunday.minusWeeks(1);
        QueryWrapper<AppAreaVisitCountDay> clone = qw.clone();
        clone.le("t_date",localSunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        clone.ge("t_date",localMonday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        List<AppAreaVisitCountDay> localWeekList =this.iAppAreaVisitCountDayService.list(clone);
        qw.le("t_date",beforeSunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        qw.ge("t_date",beforeMonday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        List<AppAreaVisitCountDay> beforeWeekList =this.iAppAreaVisitCountDayService.list(qw);
        List<Map<String,Object>> dataList = new ArrayList<>();
        List<String> cateList = Arrays.asList("周一","周二","周三","周四","周五","周六","周日");
        cateList.forEach(s-> {
            Map<String,Object> map = new HashMap<>();
            map.put("tDate",s);
            map.put("localUserCount",Long.valueOf(0));
            map.put("beforeUserCount",Long.valueOf(0));
            map.put("ratio",Long.valueOf(0));
            dataList.add(map);
        });
        localWeekList.forEach(s->{
            Integer idx = Integer.valueOf(s.gettDate());
            dataList.get(idx).put("localUserCount",s.getPlayUserCount());
        });
        beforeWeekList.forEach(s->{
            Integer idx = Integer.valueOf(s.gettDate());
            Map map = dataList.get(idx);
            map.put("beforeUserCount",s.getPlayUserCount());
            //(本周-上周)/上周 * 100
            BigDecimal localcount = BigDecimal.valueOf((Long) map.get("localUserCount"));
            BigDecimal beforeCount = BigDecimal.valueOf(s.getPlayUserCount());
            BigDecimal ratio = localcount.subtract(beforeCount).divide(beforeCount,4,1).multiply(BigDecimal.valueOf(100));
            map.put("ratio",ratio);
        });
        Map<String,CountBean> countBeanMap = new HashMap<>();
        countBeanMap.put("beforeUserCount",new CountBean("上周点播用户数","beforeUserCount","user","人","点播用户数"));
        countBeanMap.put("localUserCount",new CountBean("本周点播用户数","localUserCount","user","人","点播用户数"));
        countBeanMap.put("ratio",new CountBean("周同比","ratio","ratio","%","(本周-上周)/上周 * 100的百分比"));
        return ChartUtils.getChart(dataList,Arrays.asList("tDate"),Arrays.asList(countFields.split(",")),Arrays.asList(countFieldTypes.split(",")),countBeanMap);
    }

    /**
     * 专区区域累计访问用户饼图
     * @param userType
     * @param parentColumnId
     * @param startDate
     * @param countFields
     * @param countFieldTypes
     * @return
     */
    @GetMapping("chart/pie/totalUserCount")
    public List<Map<String,Object>> getPieChartTotalUserCount(String userType,String parentColumnId,String startDate, String countFields,String countFieldTypes) {
        QueryWrapper<AppAreaVisitCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",startDate));
        List<AppAreaVisitCountDay> datalist =this.iAppAreaVisitCountDayService.areaList(qw);
        BigDecimal totalvisitCount = BigDecimal.valueOf(datalist.stream().mapToLong(AppAreaVisitCountDay::getTotalUserCount).sum());
        List<Map<String,Object>> list = new ArrayList<>();
        datalist.forEach(s->{
            Map<String,Object> map = new HashMap<>();
            if(StringUtils.isEmpty(s.getAreaCode())){
                map.put("name","未知");
            }else{
                map.put("name",s.getAreaCode()+"-"+s.getAreaName());
            }
            map.put("y",BigDecimal.valueOf(s.getTotalUserCount()).divide(totalvisitCount,6,1).multiply(BigDecimal.valueOf(100)));
            list.add(map);
        });
        return list;
    }
    @RequiresPermissions(value = { "app_area_count:update"})
    @RequestMapping("/gengerateData")
    public ResultData gengerateData(String startDate, String endDate) {
        String[] st = startDate.split(" ");
        String sDate=st[0];
        String eDate=st[st.length-1];
        //验证时间是否符合要求
        String currDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(sDate.compareTo(eDate)>=0){
            return new ResultData("0","开始日期跟结束日期范围不匹配");
        }
        if(sDate.compareTo(currDate)>=0){
            return new ResultData("0","开始时间不能大于等于当前日期");
        }
        if(eDate.compareTo(currDate)>0){
            eDate=currDate;
        }
        this.iAppAreaVisitCountDayService.gengerateData(sDate,eDate);
        return new ResultData();
    }
}
