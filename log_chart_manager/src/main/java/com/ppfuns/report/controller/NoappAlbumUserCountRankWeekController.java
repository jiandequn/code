package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.NoappAlbumUserCountRankWeek;
import com.ppfuns.report.entity.NoappContentTypeAlbumUserCountRankWeek;
import com.ppfuns.report.service.NoappAlbumUserCountRankWeekService;
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
 * 所有专区专辑播放用户数周排行榜(NoappAlbumUserCountRankWeek)表控制层
 *
 * @author jdq
 * @since 2021-07-08 11:39:40
 */
@RestController
@RequestMapping("/report/noapp_album_user_count_rank_week")
public class NoappAlbumUserCountRankWeekController extends ChartController {
    /**
     * 服务对象
     */
    @Resource
    private NoappAlbumUserCountRankWeekService noappAlbumUserCountRankWeekService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/noapp/album_user_count_rank/week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String userType, String week, Integer page, Integer limit, String orderby) {
        QueryWrapper<NoappAlbumUserCountRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        if(StringUtils.isEmpty(orderby))
            qw.orderByAsc("user_type,content_type,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.noappAlbumUserCountRankWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<NoappAlbumUserCountRankWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(noappAlbumUserCountRankWeekService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType,Integer rankLimit, String countFields,String countFieldTypes, String week) {
        QueryWrapper<NoappAlbumUserCountRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        qw.orderByAsc("user_type,rank");
        return super.rankData(noappAlbumUserCountRankWeekService,qw,rankLimit,countFields,countFieldTypes);
    }
}
