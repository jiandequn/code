package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppContentTypeAlbumUserCountRankMonth;
import com.ppfuns.report.entity.NoappContentTypeAlbumPlayCountRankMonth;
import com.ppfuns.report.service.NoappContentTypeAlbumPlayCountRankMonthService;
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
 * 所有专区类型下专辑播放次数月排行榜(NoappContentTypeAlbumPlayCountRankMonth)表控制层
 *
 * @author jdq
 * @since 2021-07-06 17:03:14
 */
@RestController
@RequestMapping("/report/noapp_content_type_album_play_count_rank_month")
public class NoappContentTypeAlbumPlayCountRankMonthController extends ChartController {
    /**
     * 服务对象
     */
    @Resource
    private NoappContentTypeAlbumPlayCountRankMonthService noappContentTypeAlbumPlayCountRankMonthService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/noapp/content_type_album_play_count_rank/month");
        return mav;
    }

    @RequestMapping("/list")
    public ResultPage getList(String userType,String contentType, String startDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<NoappContentTypeAlbumPlayCountRankMonth> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] st = t.split("-");
            qw.eq("y",st[0]);
            qw.eq("m",st[1]);
        });
        if(StringUtils.isEmpty(orderby))
            qw.orderByAsc("user_type,content_type,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = noappContentTypeAlbumPlayCountRankMonthService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<NoappContentTypeAlbumPlayCountRankMonth> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(noappContentTypeAlbumPlayCountRankMonthService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType, String contentType, Integer rankLimit, String countFields,String countFieldTypes,String startDate) {
        QueryWrapper<NoappContentTypeAlbumPlayCountRankMonth> qw = new QueryWrapper();
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] st = t.split("-");
            qw.eq("y",st[0]);
            qw.eq("m",st[1]);
        });
        qw.orderByAsc("user_type,content_type,rank");
        return super.rankData(noappContentTypeAlbumPlayCountRankMonthService,qw,rankLimit,countFields,countFieldTypes);
    }
}
