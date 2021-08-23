package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppAlbumDurationRankMonth;
import com.ppfuns.report.entity.AppAlbumDurationRankMonth;
import com.ppfuns.report.service.AppAlbumDurationRankMonthService;
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
 * 专区专辑播放时长月排行榜(AppAlbumDurationRankMonth)表控制层
 *
 * @author jdq
 * @since 2021-07-14 15:19:00
 */
@RestController
@RequestMapping("/report/app_album_duration_rank_month")
public class AppAlbumDurationRankMonthController extends ChartController<AppAlbumDurationRankMonth> {
    /**
     * 服务对象
     */
    @Resource
    private AppAlbumDurationRankMonthService appAlbumDurationRankMonthService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/album_duration_rank/month");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId, String startDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppAlbumDurationRankMonth> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] st = t.split("-");
            qw.eq("y",st[0]);
            qw.eq("m",st[1]);
        });
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("parent_column_id,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = appAlbumDurationRankMonthService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAlbumDurationRankMonth> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appAlbumDurationRankMonthService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId,Integer rankLimit, String countFields,String countFieldTypes, String startDate) {
        QueryWrapper<AppAlbumDurationRankMonth> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t -> qw.eq("parent_column_id", parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t -> {
            String[] st = t.split("-");
            qw.eq("y", st[0]);
            qw.eq("m", st[1]);
        });
        qw.orderByDesc("parent_column_id,rank");
        return super.rankData(appAlbumDurationRankMonthService,qw,rankLimit,countFields,countFieldTypes);
    }
}
