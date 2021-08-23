package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.NoappAreaVisitCountMonth;
import com.ppfuns.report.service.INoappAreaVisitCountMonthService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 所有专区按周统计信息 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/report/noapp-area-visit-count-month")
public class NoappAreaVisitCountMonthController {
    private static final Logger logger = LoggerFactory
            .getLogger(NoappAreaVisitCountMonthController.class);
    @Autowired
    private INoappAreaVisitCountMonthService iNoappAreaVisitCountMonthService;
    /**
     * 周访问数据页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/noapp/visit_count_month");
        return mav;
    }
    @RequestMapping(value = "/toPage2", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("区域访问页！");
        ModelAndView mav = new ModelAndView("/report/noapp/area_visit_count/month/show");
        return mav;
    }
    @RequestMapping("area/list")
    public ResultPage getList2(String userType,String startDate, String areaCodes, Integer page, Integer limit, String orderby) {
        QueryWrapper<NoappAreaVisitCountMonth> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<NoappAreaVisitCountMonth>().formatMonth(qw,startDate);
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->{
            qw.in("area_code",areaCodes.split(","));
        });
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iNoappAreaVisitCountMonthService.areaList(qw);
            return new ResultPage(list.size(), list);
        }
        Page<NoappAreaVisitCountMonth> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iNoappAreaVisitCountMonthService.areaPage(pg,qw));
    }
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List list() {
        List list= this.iNoappAreaVisitCountMonthService.list();
        return list;
    }
    @RequestMapping("noArea/list")
    public ResultPage getList(String userType,String startDate, Integer page, Integer limit,String orderby) {
        QueryWrapper<NoappAreaVisitCountMonth> qw = new QueryWrapper();
        qw.select("y","m","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        new QueryConditionsUtils().formatMonth(qw,startDate);
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        qw.groupBy("y","m","user_type");
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iNoappAreaVisitCountMonthService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<NoappAreaVisitCountMonth> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iNoappAreaVisitCountMonthService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType, String startDate) {
        QueryWrapper<NoappAreaVisitCountMonth> qw = new QueryWrapper();
        qw.select("y","m","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        new QueryConditionsUtils().formatMonth(qw,startDate);
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));

        qw.groupBy("y","m","user_type");
        List<NoappAreaVisitCountMonth> userList =this.iNoappAreaVisitCountMonthService.list(qw);
        List categories = new ArrayList<>(userList.size());
        List data = new ArrayList<>(userList.size());
        List playUserData = new ArrayList<>(userList.size());
        List playCountData = new ArrayList<>(userList.size());
        List durationData = new ArrayList<>(userList.size());
        userList.forEach(s->{
            categories.add(s.getY()+"年"+s.getM()+"月");
            data.add(s.getPageUserCount());
            playUserData.add(s.getPlayUserCount());
            playCountData.add(s.getPlayCount());
            durationData.add(s.getDuration()/3600);
        });
        Map<String,Object> serieMap = new HashMap<>();
        serieMap.put("name","访问用户数");
        serieMap.put("data",data);

        Map<String,Object> serieMap2 = new HashMap<>();
        serieMap2.put("name","播放用户数");
        serieMap2.put("data",playUserData);
        Map<String,Object> serieMap3 = new HashMap<>();
        serieMap3.put("name","点播次数");
        serieMap3.put("data",playCountData);
        Map<String,Object> serieMap4 = new HashMap<>();
        serieMap4.put("name","播放时长");
        serieMap4.put("data",durationData);

        List series = new ArrayList<>();
        series.add(serieMap);
        series.add(serieMap2);
        series.add(serieMap3);
        series.add(serieMap4);
        Map<String,Object> result = new HashMap<>();
        result.put("series",series);
        result.put("categories",categories);
        return result;
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response,  String startDate,String userType)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<NoappAreaVisitCountMonth> qw = new QueryWrapper();
        qw.select("y","m","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        new QueryConditionsUtils().formatMonth(qw,startDate);
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        qw.groupBy("y","m","user_type");
        List<NoappAreaVisitCountMonth> userList =this.iNoappAreaVisitCountMonthService.list(qw);
        EasyExcel.write(out, NoappAreaVisitCountMonth.class).sheet("新增用户统计").doWrite(userList);
    }
}
