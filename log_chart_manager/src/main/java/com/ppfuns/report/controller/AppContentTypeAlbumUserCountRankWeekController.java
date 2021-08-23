package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppContentTypeAlbumPlayCountRankWeek;
import com.ppfuns.report.entity.AppContentTypeAlbumUserCountRankWeek;
import com.ppfuns.report.service.AppContentTypeAlbumUserCountRankWeekService;
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
 * 专区类型下专辑播放用户数周排行榜(AppContentTypeAlbumUserCountRankWeek)表控制层
 *
 * @author jdq
 * @since 2021-07-06 16:58:15
 */
@RestController
@RequestMapping("/report/app_content_type_album_user_count_rank_week")
public class AppContentTypeAlbumUserCountRankWeekController extends ChartController {
    /**
     * 服务对象
     */
    @Resource
    private AppContentTypeAlbumUserCountRankWeekService appContentTypeAlbumUserCountRankWeekService;


    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/content_type_album_user_count_rank/week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId,String contentType, String week, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppContentTypeAlbumUserCountRankWeek> qw = new QueryWrapper();
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
            List list = this.appContentTypeAlbumUserCountRankWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppContentTypeAlbumUserCountRankWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appContentTypeAlbumUserCountRankWeekService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId,String contentType,Integer rankLimit, String countFields,String countFieldTypes, String week) {
        QueryWrapper<AppContentTypeAlbumUserCountRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t -> qw.eq("content_type", contentType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t -> qw.eq("parent_column_id", parentColumnId));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t -> {
            String[] weeks = week.split("-");
            qw.eq("y", weeks[0]).eq("w", weeks[1]);
        });
        qw.orderByAsc("parent_column_id,content_type,rank");
        return super.rankData(appContentTypeAlbumUserCountRankWeekService, qw, rankLimit, countFields, countFieldTypes);
    }
}
