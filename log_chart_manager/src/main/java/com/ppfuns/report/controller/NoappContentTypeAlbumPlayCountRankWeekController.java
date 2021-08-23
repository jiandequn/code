package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppContentTypeAlbumUserCountRankWeek;
import com.ppfuns.report.entity.NoappContentTypeAlbumPlayCountRankWeek;
import com.ppfuns.report.entity.NoappContentTypeAlbumUserCountRankWeek;
import com.ppfuns.report.service.NoappContentTypeAlbumPlayCountRankWeekService;
import javafx.scene.chart.Chart;
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
 * 所有专区类型下专辑播放次数周排行榜(NoappContentTypeAlbumPlayCountRankWeek)表控制层
 *
 * @author jdq
 * @since 2021-07-06 17:03:15
 */
@RestController
@RequestMapping("/report/noapp_content_type_album_play_count_rank_week")
public class NoappContentTypeAlbumPlayCountRankWeekController extends ChartController {
    /**
     * 服务对象
     */
    @Resource
    private NoappContentTypeAlbumPlayCountRankWeekService noappContentTypeAlbumPlayCountRankWeekService;
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/noapp/content_type_album_play_count_rank/week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String userType,String contentType, String week, Integer page, Integer limit, String orderby) {
        QueryWrapper<NoappContentTypeAlbumPlayCountRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        if(StringUtils.isEmpty(orderby))
            qw.orderByAsc("user_type,content_type,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.noappContentTypeAlbumPlayCountRankWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<NoappContentTypeAlbumPlayCountRankWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(noappContentTypeAlbumPlayCountRankWeekService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType,String contentType,Integer rankLimit, String countFields,String countFieldTypes, String week) {
        QueryWrapper<NoappContentTypeAlbumPlayCountRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        qw.orderByAsc("user_type,content_type,rank");
        return super.rankData(noappContentTypeAlbumPlayCountRankWeekService,qw,rankLimit,countFields,countFieldTypes);
    }
}
