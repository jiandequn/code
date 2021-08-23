package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountWeek;
import com.ppfuns.report.entity.AppDetailPageCountWeek;
import com.ppfuns.report.service.IAppDetailPageCountWeekService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 按周统计详情访问 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/report/app-detail-page-count-week")
public class AppDetailPageCountWeekController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppDetailPageCountWeekController.class);
    @Autowired
    private IAppDetailPageCountWeekService iAppDetailPageCountWeekService;
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/detail_page_count_week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String userType,String parentColumnId, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppDetailPageCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppDetailPageCountWeek>().formatWeek(qw,startDate,endDate);
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iAppDetailPageCountWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppDetailPageCountWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppDetailPageCountWeekService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType,String parentColumnId, String startDate, String endDate) {
        QueryWrapper<AppDetailPageCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppDetailPageCountWeek>().formatWeek(qw,startDate,endDate);
        List<AppDetailPageCountWeek> userList =this.iAppDetailPageCountWeekService.list(qw);
        List categories = new ArrayList<>(userList.size());
        List userCountdata = new ArrayList<>(userList.size());
        List visitCountdata = new ArrayList<>(userList.size());
        userList.forEach(s->{
            categories.add(s.getY()+"年"+s.getW()+"周");
            userCountdata.add(s.getUserCount());
            visitCountdata.add(s.getVisitCount());
        });
        Map<String,Object> serieMap = new HashMap<>();
        serieMap.put("name","用户数");
        serieMap.put("type","column");
        serieMap.put("yAxis",1);
        serieMap.put("data",userCountdata);

        Map<String,Object> serieMap2 = new HashMap<>();
        serieMap2.put("name","点击次数");
        serieMap.put("type","spline");
        serieMap2.put("data",visitCountdata);
        List series = new ArrayList<>();
        series.add(serieMap);
        series.add(serieMap2);
        Map<String,Object> result = new HashMap<>();
        result.put("series",series);
        result.put("categories",categories);
        return result;
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response,String userType, String parentColumnId, String startDate, String endDate)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppDetailPageCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppDetailPageCountWeek>().formatWeek(qw,startDate,endDate);
        List<AppDetailPageCountWeek> userList =this.iAppDetailPageCountWeekService.list(qw);
        EasyExcel.write(out, AppAreaVisitCountWeek.class).sheet("新增用户统计").doWrite(userList);
    }
}
