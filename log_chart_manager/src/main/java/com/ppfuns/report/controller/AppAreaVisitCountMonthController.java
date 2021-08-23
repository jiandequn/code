package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountMonth;
import com.ppfuns.report.service.IAppAreaVisitCountMonthService;
import com.ppfuns.report.utils.ChartUtils;
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
 * 专区按月统计信息 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/report/app-area-visit-count-month")
public class AppAreaVisitCountMonthController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppAreaVisitCountMonthController.class);
    @Autowired
    private IAppAreaVisitCountMonthService iAppAreaVisitCountMonthService;
    /**
     * 周访问数据页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/visit_count_month");
        return mav;
    }
    @RequestMapping(value = "/toPage2", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("区域访问页！");
        ModelAndView mav = new ModelAndView("/report/app/area_visit_count/month/show");
        return mav;
    }
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List list() {
        List list= this.iAppAreaVisitCountMonthService.list();
        return list;
    }
    @RequestMapping("noArea/list")
    public ResultPage getList(String userType,String parentColumnId, String startDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppAreaVisitCountMonth> qw = new QueryWrapper();
        qw.select("y","m","parent_column_id","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppAreaVisitCountMonth>().formatMonth(qw,startDate);
        qw.groupBy("y","m","parent_column_id","user_type");
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iAppAreaVisitCountMonthService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAreaVisitCountMonth> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppAreaVisitCountMonthService.page(pg,qw));
    }
    @RequestMapping("area/list")
    public ResultPage getList2(String userType,String parentColumnId, String startDate,String areaCodes, Integer page, Integer limit,String orderby) {
        QueryWrapper<AppAreaVisitCountMonth> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppAreaVisitCountMonth>().formatMonth(qw,startDate);
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->{
            qw.in("area_code",areaCodes.split(","));
        });
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iAppAreaVisitCountMonthService.areaList(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAreaVisitCountMonth> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppAreaVisitCountMonthService.areaPage(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId, String userType,String startDate, String countFields,String countFieldTypes) {
        QueryWrapper<AppAreaVisitCountMonth> qw = new QueryWrapper();
        qw.select("y","m","parent_column_id","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppAreaVisitCountMonth>().formatMonth(qw,startDate);
        qw.groupBy("y","m","parent_column_id","user_type");
        List<AppAreaVisitCountMonth> userList =this.iAppAreaVisitCountMonthService.list(qw);
        return ChartUtils.getChart(userList,Arrays.asList("y","m"),Arrays.asList(countFields.split(",")),Arrays.asList(countFieldTypes.split(",")));

    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String userType,String parentColumnId, String startDate)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppAreaVisitCountMonth> qw = new QueryWrapper();
        qw.select("y","m","parent_column_id","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppAreaVisitCountMonth>().formatMonth(qw,startDate);
        qw.groupBy("y","m","parent_column_id","user_type");
        List<AppAreaVisitCountMonth> userList =this.iAppAreaVisitCountMonthService.list(qw);
        EasyExcel.write(out, AppAreaVisitCountMonth.class).sheet("专区内专辑类型月统计").doWrite(userList);
    }
}
