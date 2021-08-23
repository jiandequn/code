package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppInletCountDay;
import com.ppfuns.report.service.AppInletCountDayService;
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
 * 用户来源按日统计(AppInletCountDay)表控制层
 *
 * @author jdq
 * @since 2021-05-25 09:51:38
 */
@RestController
@RequestMapping("/report/appInletCountDay")
public class AppInletCountDayController extends ApiController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppInletCountDayController.class);
    @Autowired
    private AppInletCountDayService appInletCountDayService;
    /**
     * 日访问数据页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/inlet_count_day");
        return mav;
    }
    @RequestMapping("list")
    public ResultPage getList(String userType,String parentColumnId, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppInletCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.appInletCountDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppInletCountDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appInletCountDayService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType,String parentColumnId, String startDate, String endDate) {
        QueryWrapper<AppInletCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        List<AppInletCountDay> userList =this.appInletCountDayService.list(qw);
        List categories = new ArrayList<>(userList.size());
        List data = new ArrayList<>(userList.size());
        List visitCountData = new ArrayList<>(userList.size());
        List durationData = new ArrayList<>(userList.size());
        userList.forEach(s->{
            categories.add(s.gettDate());
            data.add(s.getUserCount());
            visitCountData.add(s.getVisitCount());
        });
        Map<String,Object> serieMap = new HashMap<>();
        serieMap.put("name","用户数");
        serieMap.put("data",data);
        Map<String,Object> serieMap3 = new HashMap<>();
        serieMap3.put("name","次数");
        serieMap3.put("data",visitCountData);
        List series = new ArrayList<>();
        series.add(serieMap);
        series.add(serieMap3);
        Map<String,Object> result = new HashMap<>();
        result.put("series",series);
        result.put("categories",categories);
        return result;
    }
    @RequestMapping("/chart/list2")
    public Map<String,Object> getChartList2(String userType, String parentColumnId, String startDate, String endDate) {
        QueryWrapper<AppInletCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        List<AppInletCountDay> userList =this.appInletCountDayService.list(qw);
        List<String> xData=new ArrayList<>();//时间
        List<Map> datasets=new ArrayList<>();
        Map<String,Object> data = new HashMap<>();
        data.put("name","");//专辑类型
        data.put("data",new int[5]);//专辑类型
        data.put("unit","个数");
        data.put("type","area");
        data.put("valueDecimals",0);
        Map<String,Object> res = new HashMap<>();
        res.put("xData",xData);
        res.put("datasets",datasets);
        return res;
//        List categories = new ArrayList<>(userList.size());
//        List data = new ArrayList<>(userList.size());
//        List playCountData = new ArrayList<>(userList.size());
//        List durationData = new ArrayList<>(userList.size());
//        userList.forEach(s->{
//            categories.add(s.gettDate());
//            data.add(s.getUserCount());
//            playCountData.add(s.getPlayCount());
//            durationData.add(s.getDuration()/3600);
//        });
//        Map<String,Object> serieMap = new HashMap<>();
//        serieMap.put("name","播放用户数");
//        serieMap.put("data",data);
//        Map<String,Object> serieMap3 = new HashMap<>();
//        serieMap3.put("name","点播次数");
//        serieMap3.put("data",playCountData);
//        Map<String,Object> serieMap4 = new HashMap<>();
//        serieMap4.put("name","播放时长");
//        serieMap4.put("data",durationData);
//
//        List series = new ArrayList<>();
//        series.add(serieMap);
//        series.add(serieMap3);
//        series.add(serieMap4);
//        Map<String,Object> result = new HashMap<>();
//        result.put("series",series);
//        result.put("categories",categories);
//        return result;
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String userType,String parentColumnId, String startDate, String endDate)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppInletCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        List<AppInletCountDay> userList =this.appInletCountDayService.list(qw);
        EasyExcel.write(out, AppInletCountDay.class).sheet("专区内专辑类型日统计").doWrite(userList);
    }
}
