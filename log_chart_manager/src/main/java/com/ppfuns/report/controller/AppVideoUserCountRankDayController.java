package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppVideoUserCountRankDay;
import com.ppfuns.report.service.IAppVideoUserCountRankDayService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
 * 专辑播放用户数排行榜 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/report/app-video-user-count-rank-day")
public class AppVideoUserCountRankDayController extends ChartController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppVideoUserCountRankDayController.class);
    @Autowired
    private IAppVideoUserCountRankDayService iAppVideoUserCountRankDayService;
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/video_user_count_rank_day");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId, String date, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppVideoUserCountRankDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("parent_column_id,user_count");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iAppVideoUserCountRankDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppVideoUserCountRankDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppVideoUserCountRankDayService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId, Integer rankLimit, String countFields,String countFieldTypes,String date) {
        QueryWrapper<AppVideoUserCountRankDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        qw.orderByDesc("parent_column_id,user_count");
        return super.rankData(iAppVideoUserCountRankDayService,qw,rankLimit,countFields,countFieldTypes);
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String parentColumnId, String date)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppVideoUserCountRankDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        qw.orderByDesc("parent_column_id,user_count");
        List<AppVideoUserCountRankDay> userList =this.iAppVideoUserCountRankDayService.list(qw);
        EasyExcel.write(out, AppVideoUserCountRankDay.class).sheet("新增用户统计").doWrite(userList);
    }
}
