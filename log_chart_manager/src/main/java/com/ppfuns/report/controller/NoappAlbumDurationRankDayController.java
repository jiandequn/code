package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.NoappAlbumDurationRankDay;
import com.ppfuns.report.entity.NoappAlbumDurationRankDay;
import com.ppfuns.report.service.NoappAlbumDurationRankDayService;
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
 * 所有专区专辑播放时长周排行榜(NoappAlbumDurationRankDay)表控制层
 *
 * @author jdq
 * @since 2021-07-14 15:21:58
 */
@RestController
@RequestMapping("/report/noapp_album_duration_rank_day")
public class NoappAlbumDurationRankDayController extends ChartController {
    /**
     * 服务对象
     */
    @Resource
    private NoappAlbumDurationRankDayService noappAlbumDurationRankDayService;


    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/noapp/album_duration_rank/day");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String userType, String date, Integer page, Integer limit, String orderby) {
        QueryWrapper<NoappAlbumDurationRankDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        if(StringUtils.isEmpty(orderby))
            qw.orderByAsc("user_type,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.noappAlbumDurationRankDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<NoappAlbumDurationRankDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(noappAlbumDurationRankDayService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType,Integer rankLimit, String countFields,String countFieldTypes, String date) {
        QueryWrapper<NoappAlbumDurationRankDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t -> qw.eq("user_type", userType));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t -> qw.eq("t_date", date));
        qw.orderByAsc("user_type,rank");
        return super.rankData(noappAlbumDurationRankDayService, qw, rankLimit, countFields, countFieldTypes);
    }
}
