package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppCountDay;
import com.ppfuns.report.entity.NoappCountDay;
import com.ppfuns.report.service.AppCountDayService;
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
import java.util.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.alibaba.excel.EasyExcel;

/**
 * 专区日统计(AppCountDay)表控制层
 *
 * @author jdq
 * @since 2021-07-12 17:27:52
 */
@RestController
@RequestMapping("/report/app_count_day")
public class AppCountDayController extends ChartController<AppCountDay> {
    /**
     * 服务对象
     */
    @Resource
    private AppCountDayService appCountDayService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app_count_day");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String userType,String parentColumnId,String countFields, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        int countFlag=1;
        if(!StringUtils.isEmpty(countFields)){
            List<String> countFieldList = Arrays.asList(countFields.split(","));
            boolean countDayFlag = countFieldList.stream().anyMatch(s->s.equals("visitUserCount")||s.equals("playUserCount")||s.equals("playCount")
                    ||s.equals("duration")||s.equals("addUserCount")||s.equals("totalVisitUserCount"));
            boolean retentionFlag = countFieldList.stream().anyMatch(s->s.equals("user2dayCount")||s.equals("user3dayCount")||s.equals("addUser2dayCount"));
            countFlag = countDayFlag && retentionFlag ? 3 : countDayFlag ? 1 : 2;
        }
        if (page == null || limit == null) {
            List list = this.appCountDayService.mergeList(qw,countFlag);
            return new ResultPage(list.size(), list);
        }
        Page<AppCountDay> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(appCountDayService.mergePage(pg, qw,countFlag));
    }
    @RequiresPermissions(value = { "app_count:update"})
    @PostMapping("gengerateData")
    public ResultData gengerateData(String startDate) {
        String[] st = startDate.split(" ");
        this.appCountDayService.gengerateData(st[0],st[st.length-1]);
        return new ResultData();
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType,String parentColumnId, String countFields,String countFieldTypes, String startDate, String endDate) {
        QueryWrapper<AppCountDay> qw = new QueryWrapper();
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        int countFlag=1;
        List<String> countFieldList = Arrays.asList(countFields.split(","));
        if(!StringUtils.isEmpty(countFields)){
            boolean countDayFlag = countFieldList.stream().anyMatch(s->s.equals("visitUserCount")||s.equals("playUserCount")||s.equals("playCount")
                    ||s.equals("duration")||s.equals("addUserCount")||s.equals("totalVisitUserCount"));
            boolean retentionFlag = countFieldList.stream().anyMatch(s->s.equals("user2dayCount")||s.equals("user3dayCount")||s.equals("addUser2dayCount"));
            countFlag = countDayFlag && retentionFlag ? 3 : countDayFlag ? 1 : 2;
        }
        List<AppCountDay> list = this.appCountDayService.mergeList(qw,countFlag);
        List<String> cates = Arrays.asList("tDate");
        Map<String,Object> result = ChartUtils.getChart(list,cates,countFieldList,Arrays.asList(countFieldTypes.split(",")));
        return result;
    }
}
