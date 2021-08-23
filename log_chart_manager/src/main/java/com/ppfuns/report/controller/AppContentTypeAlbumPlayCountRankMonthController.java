package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppAlbumUserCountRankMonth;
import com.ppfuns.report.entity.AppContentTypeAlbumPlayCountRankMonth;
import com.ppfuns.report.service.AppContentTypeAlbumPlayCountRankMonthService;
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
 * 专区类型下专辑播放次数月排行榜(AppContentTypeAlbumPlayCountRankMonth)表控制层
 *
 * @author jdq
 * @since 2021-07-06 16:58:14
 */
@RestController
@RequestMapping("/report/app_content_type_album_play_count_rank_month")
public class AppContentTypeAlbumPlayCountRankMonthController extends ChartController {
    /**
     * 服务对象
     */
    @Resource
    private AppContentTypeAlbumPlayCountRankMonthService appContentTypeAlbumPlayCountRankMonthService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/content_type_album_play_count_rank/month");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId,String contentType, String startDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppContentTypeAlbumPlayCountRankMonth> qw = new QueryWrapper();
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",t));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",t));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] st = t.split("-");
            qw.eq("y",st[0]);
            qw.eq("m",st[1]);
        });
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("parent_column_id,content_type,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = appContentTypeAlbumPlayCountRankMonthService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppContentTypeAlbumPlayCountRankMonth> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appContentTypeAlbumPlayCountRankMonthService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId,String contentType,Integer rankLimit, String countFields,String countFieldTypes, String startDate) {
        QueryWrapper<AppContentTypeAlbumPlayCountRankMonth> qw = new QueryWrapper();
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] st = t.split("-");
            qw.eq("y",st[0]);
            qw.eq("m",st[1]);
        });
        qw.orderByAsc("parent_column_id,content_type,rank");
        return super.rankData(appContentTypeAlbumPlayCountRankMonthService,qw,rankLimit,countFields,countFieldTypes);
    }
}
