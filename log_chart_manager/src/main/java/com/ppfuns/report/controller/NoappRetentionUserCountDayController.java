package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppRetentionUserCountDay;
import com.ppfuns.report.entity.NoappRetentionUserCountDay;
import com.ppfuns.report.service.NoappRetentionUserCountDayService;
import org.springframework.web.bind.annotation.*;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.alibaba.excel.EasyExcel;
import org.thymeleaf.util.StringUtils;

/**
 * 所有专区留存用户数统计(NoappRetentionUserCountDay)表控制层
 *
 * @author jdq
 * @since 2021-07-06 17:03:50
 */
@RestController
@RequestMapping("/report/noapp_retention_user_count_day")
public class NoappRetentionUserCountDayController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private NoappRetentionUserCountDayService noappRetentionUserCountDayService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/noapp/retention_user_count_day/list");
        return mav;
    }

    @RequestMapping("/list")
    public ResultPage getList(String userType, String startDate , String endDate, Integer retentionType, Integer page, Integer limit, String orderby) {
        QueryWrapper<NoappRetentionUserCountDay> qw = new QueryWrapper();
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        QueryConditionsUtils.formatOrderByWithClazz(qw,orderby,NoappRetentionUserCountDay.class,null);
        if(page ==null || limit == null){
            List list = noappRetentionUserCountDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<NoappRetentionUserCountDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(noappRetentionUserCountDayService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType, String startDate , String endDate, Integer retentionType, String orderby) {
        QueryWrapper<NoappRetentionUserCountDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        if(StringUtils.isEmpty(orderby)){
            qw.orderByAsc("t_date");
        }else{
            QueryConditionsUtils.formatOrderByWithClazz(qw,orderby,NoappRetentionUserCountDay.class,null);
        }
        List<NoappRetentionUserCountDay> userList =this.noappRetentionUserCountDayService.list(qw);
        List categories = new ArrayList<>(userList.size());
        List data = new ArrayList<>(userList.size());
        List data1 = new ArrayList<>(userList.size());
        userList.forEach(s->{
            categories.add(s.gettDate());
            data.add(s.getUser2dayCount());
            data1.add(s.getUser3dayCount());
        });
        Map<String,Object> serieMap = new HashMap<>();
        serieMap.put("name","2日留存用户");
        serieMap.put("data",data);
        Map<String,Object> serieMap1 = new HashMap<>();
        serieMap1.put("name","3日留存用户");
        serieMap1.put("data",data1);
        List series = new ArrayList<>();
        if(retentionType == null || retentionType == 0){
            series.add(serieMap);
            series.add(serieMap1);
        }else if(retentionType == 1){
            series.add(serieMap);
        }else if(retentionType == 2){
            series.add(serieMap1);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("series",series);
        result.put("categories",categories);
        return result;
    }


}
