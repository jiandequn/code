package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppRetentionUserCountDay;
import com.ppfuns.report.entity.NoappRetentionUserCountDay;
import com.ppfuns.report.service.IAppRetentionUserCountDayService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 留存用户数统计 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-23
 */
@RestController
@RequestMapping("/report/app-retention-user-count-day")
public class AppRetentionUserCountDayController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppRetentionUserCountDayController.class);
    @Autowired
    private IAppRetentionUserCountDayService iAppRetentionUserCountDayService;
    /**
     * 周访问数据页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/retention_user_count_day");
        return mav;
    }
    @RequestMapping(value = "/toPage2", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/retention_user");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String userType,String parentColumnId, String startDate , String endDate, Integer retentionType, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppRetentionUserCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        QueryConditionsUtils.formatOrderByWithClazz(qw,orderby,AppRetentionUserCountDay.class,null);
        if(page ==null || limit == null){
            List list = iAppRetentionUserCountDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppRetentionUserCountDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppRetentionUserCountDayService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType,String parentColumnId,String startDate ,String endDate,Integer retentionType,String orderby) {
        QueryWrapper<AppRetentionUserCountDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        if(StringUtils.isEmpty(orderby)){
            qw.orderByAsc("t_date");
        }else{
            QueryConditionsUtils.formatOrderByWithClazz(qw,orderby, AppRetentionUserCountDay.class,null);
        }
        List<AppRetentionUserCountDay> userList =this.iAppRetentionUserCountDayService.list(qw);
        List categories = new ArrayList<>(userList.size());
        List data = new ArrayList<>(userList.size());
        List data1 = new ArrayList<>(userList.size());
        List data2= new ArrayList<>(userList.size());
        userList.forEach(s->{
            categories.add(s.gettDate());
            data.add(s.getUser2dayCount());
            data1.add(s.getUser3dayCount());
            data2.add(s.getAddUser2dayCount());
        });
        Map<String,Object> serieMap = new HashMap<>();
        serieMap.put("name","2日留存用户");
        serieMap.put("data",data);
        Map<String,Object> serieMap1 = new HashMap<>();
        serieMap1.put("name","3日留存用户");
        serieMap1.put("data",data1);
        Map<String,Object> serieMap2 = new HashMap<>();
        serieMap2.put("name","新增用户2日留存用户");
        serieMap2.put("data",data2);
        List series = new ArrayList<>();
        if(retentionType == null || retentionType == 0){
            series.add(serieMap);
            series.add(serieMap1);
            series.add(serieMap2);
        }else if(retentionType == 1){
            series.add(serieMap);
        }else if(retentionType == 2){
            series.add(serieMap1);
        }else if(retentionType == 3){
            series.add(serieMap2);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("series",series);
        result.put("categories",categories);
        return result;
    }
    @GetMapping("/export")
    public void export(String fileName, HttpServletResponse response, String userType,String parentColumnId, String dateType, String startDate, String endDate, String year, String month, String week)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppRetentionUserCountDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        List data = this.iAppRetentionUserCountDayService.list(qw);
        EasyExcel.write(out, AppRetentionUserCountDay.class).sheet("新增用户统计").doWrite(data);
    }
}
