package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppInletCountWeek;
import com.ppfuns.report.service.IAppInletCountWeekService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 用户来源按日统计 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-12-28
 */
@RestController
@RequestMapping("/report/app-inlet-count-week")
public class AppInletCountWeekController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppInletCountWeekController.class);
    @Autowired
    private IAppInletCountWeekService iAppInletCountWeekService;
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/inlet_count_week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String userType,String parentColumnId, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppInletCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppInletCountWeek>().formatWeek(qw,startDate,endDate);
        if(StringUtils.isEmpty(orderby))
            qw.orderByAsc("y","w","user_type","parent_column_id");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iAppInletCountWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppInletCountWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppInletCountWeekService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType,String parentColumnId, @RequestParam(defaultValue = "week") String dateType, String startDate, String endDate, String year, String month, String week, String areaFlag) {
        QueryWrapper<AppInletCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppInletCountWeek>().formatWeek(qw,startDate,endDate);
        qw.orderByAsc("y","w","user_type","parent_column_id");
        List<AppInletCountWeek> userList =this.iAppInletCountWeekService.list(qw);
        List categories = new ArrayList<>(userList.size());
        List data = new ArrayList<>(userList.size());
        List playCountData = new ArrayList<>(userList.size());
        userList.forEach(s->{
            categories.add(s.getY()+"年"+s.getW()+"周");
            data.add(s.getUserCount());
            playCountData.add(s.getVisitCount());
        });
        Map<String,Object> serieMap = new HashMap<>();
        serieMap.put("name","访问用户数");
        serieMap.put("data",data);

        Map<String,Object> serieMap3 = new HashMap<>();
        serieMap3.put("name","点击次数");
        serieMap3.put("data",playCountData);

        List series = new ArrayList<>();
        series.add(serieMap);
        series.add(serieMap3);
        Map<String,Object> result = new HashMap<>();
        result.put("series",series);
        result.put("categories",categories);
        return result;
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response,String userType, String parentColumnId, @RequestParam(defaultValue = "week") String dateType, String startDate, String endDate, String year, String month, String week, String areaFlag)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppInletCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppInletCountWeek>().formatWeek(qw,startDate,endDate);
        qw.orderByAsc("y","w","user_type","parent_column_id");
        List<AppInletCountWeek> userList =this.iAppInletCountWeekService.list(qw);
        EasyExcel.write(out, AppInletCountWeek.class).sheet("新增用户统计").doWrite(userList);
    }
}
