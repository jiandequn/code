package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppContentTypeAlbumDurationRankWeek;
import com.ppfuns.report.entity.AppContentTypeAlbumDurationRankWeek;
import com.ppfuns.report.service.AppContentTypeAlbumDurationRankWeekService;
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
 * 专区类型下专辑播放时长周排行榜(AppContentTypeAlbumDurationRankWeek)表控制层
 *
 * @author jdq
 * @since 2021-07-14 15:20:46
 */
@RestController
@RequestMapping("/report/app_content_type_album_duration_rank_week")
public class AppContentTypeAlbumDurationRankWeekController extends ChartController {
    /**
     * 服务对象
     */
    @Resource
    private AppContentTypeAlbumDurationRankWeekService appContentTypeAlbumDurationRankWeekService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/content_type_album_duration_rank/week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId,String contentType, String week, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppContentTypeAlbumDurationRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("parent_column_id,content_type,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.appContentTypeAlbumDurationRankWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppContentTypeAlbumDurationRankWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appContentTypeAlbumDurationRankWeekService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId, String contentType,Integer rankLimit, String countFields,String countFieldTypes, String week) {
        QueryWrapper<AppContentTypeAlbumDurationRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        qw.orderByAsc("parent_column_id,content_type,rank");
        return super.rankData(appContentTypeAlbumDurationRankWeekService,qw,rankLimit,countFields,countFieldTypes);
    }
}
