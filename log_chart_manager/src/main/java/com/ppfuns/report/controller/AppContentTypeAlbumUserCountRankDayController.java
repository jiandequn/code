package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppContentTypeAlbumPlayCountRankDay;
import com.ppfuns.report.entity.AppContentTypeAlbumUserCountRankDay;
import com.ppfuns.report.service.AppContentTypeAlbumUserCountRankDayService;
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
 * 专区类型下专辑播放用户数日排行榜(AppContentTypeAlbumUserCountRankDay)表控制层
 *
 * @author jdq
 * @since 2021-07-06 16:58:15
 */
@RestController
@RequestMapping("/report/app_content_type_album_user_count_rank_day")
public class AppContentTypeAlbumUserCountRankDayController extends ChartController {
    /**
     * 服务对象
     */
    @Resource
    private AppContentTypeAlbumUserCountRankDayService appContentTypeAlbumUserCountRankDayService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/content_type_album_user_count_rank/day");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId,String contentType, String date, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppContentTypeAlbumUserCountRankDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("parent_column_id,content_type,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.appContentTypeAlbumUserCountRankDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppContentTypeAlbumUserCountRankDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appContentTypeAlbumUserCountRankDayService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId,Integer rankLimit, String countFields,String countFieldTypes, String date, String contentType) {
        QueryWrapper<AppContentTypeAlbumUserCountRankDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        qw.orderByAsc("parent_column_id,content_type,rank");
        return super.rankData(appContentTypeAlbumUserCountRankDayService,qw,rankLimit,countFields,countFieldTypes);
    }
}
