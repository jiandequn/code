package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppCountDay;
import com.ppfuns.report.entity.NoappAreaVisitCountDay;
import com.ppfuns.report.entity.NoappCountDay;
import com.ppfuns.report.entity.base.CountBean;
import com.ppfuns.report.service.NoappCountDayService;
import com.ppfuns.report.utils.ChartUtils;
import com.ppfuns.util.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.print.DocFlavor;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.alibaba.excel.EasyExcel;

/**
 * 所有专区日统计(NoappCountDay)表控制层
 *
 * @author jdq
 * @since 2021-07-12 17:28:10
 */
@RestController
@RequestMapping("/report/noapp_count_day")
public class NoappCountDayController extends ChartController<NoappCountDay> {
    /**
     * 服务对象
     */
    @Resource
    private NoappCountDayService noappCountDayService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/noapp_count_day");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String userType,String countFields, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<NoappCountDay> qw = new QueryWrapper();
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        int countFlag=1;
        if(!StringUtils.isEmpty(countFields)){
            boolean countDayFlag = Arrays.stream(countFields.split(",")).anyMatch(s->s.equals("visitUserCount")||s.equals("playUserCount")||s.equals("playCount")
                    ||s.equals("duration")||s.equals("addUserCount")||s.equals("totalVisitUserCount"));
            boolean retentionFlag = Arrays.stream(countFields.split(",")).anyMatch(s->s.equals("user2dayCount")||s.equals("user3dayCount")||s.equals("addUser2dayCount"));
            countFlag = countDayFlag && retentionFlag ? 3 : countDayFlag ? 1 : 2;
        }
        if (page == null || limit == null) {
            List list = this.noappCountDayService.mergeList(qw,countFlag);
            return new ResultPage(list.size(), list);
        }
        Page<NoappCountDay> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(noappCountDayService.mergePage(pg, qw,countFlag));
    }
    @RequiresPermissions(value = { "noapp_count:update"})
    @PostMapping("gengerateData")
    public ResultData gengerateData(String startDate) {
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
        this.noappCountDayService.gengerateData(sDate,eDate);
        return new ResultData();
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType, String countFields,String countFieldTypes,String startDate, String endDate) {
        QueryWrapper<NoappCountDay> qw = new QueryWrapper();
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        int countFlag=1;
        List<String> countFieldList = Arrays.asList(countFields.split(","));
        if(!StringUtils.isEmpty(countFields)){
            boolean countDayFlag = countFieldList.stream().anyMatch(s->s.equals("visitUserCount")||s.equals("playUserCount")||s.equals("playCount")
                    ||s.equals("duration")||s.equals("addUserCount")||s.equals("totalVisitUserCount"));
            boolean retentionFlag = countFieldList.stream().anyMatch(s->s.equals("user2dayCount")||s.equals("user3dayCount")||s.equals("addUser2dayCount"));
            countFlag = countDayFlag && retentionFlag ? 3 : countDayFlag ? 1 : 2;
        }
        List<NoappCountDay> list = this.noappCountDayService.mergeList(qw,countFlag);

        Map<String,Object> result = ChartUtils.getChart(list,Arrays.asList("tDate"),countFieldList,Arrays.asList(countFieldTypes.split(",")));
        return result;
    }

}
