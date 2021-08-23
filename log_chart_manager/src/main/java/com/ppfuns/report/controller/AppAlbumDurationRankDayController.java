package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppAlbumDurationRankDay;
import com.ppfuns.report.entity.AppAlbumDurationRankDay;
import com.ppfuns.report.service.AppAlbumDurationRankDayService;
import org.springframework.util.StringUtils;
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

/**
 * 专区专辑播放时长周排行榜(AppAlbumDurationRankDay)表控制层
 *
 * @author jdq
 * @since 2021-07-14 15:19:00
 */
@RestController
@RequestMapping("/report/app_album_duration_rank_day")
public class AppAlbumDurationRankDayController extends ChartController<AppAlbumDurationRankDay> {
    /**
     * 服务对象
     */
    @Resource
    private AppAlbumDurationRankDayService appAlbumDurationRankDayService;
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/album_duration_rank/day");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId, String date, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppAlbumDurationRankDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("parent_column_id,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = appAlbumDurationRankDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAlbumDurationRankDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appAlbumDurationRankDayService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId,Integer rankLimit, String countFields,String countFieldTypes, String date) {
        QueryWrapper<AppAlbumDurationRankDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        qw.orderByDesc("parent_column_id,rank");
        return super.rankData(appAlbumDurationRankDayService,qw,rankLimit,countFields,countFieldTypes);
    }
}
