package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppAlbumUserCountRankDay;
import com.ppfuns.report.entity.AppAlbumUserCountRankMonth;
import com.ppfuns.report.entity.AppAlbumUserCountRankWeek;
import com.ppfuns.report.service.AppAlbumUserCountRankMonthService;
import com.ppfuns.report.utils.ChartUtils;
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
 * 专区专辑播放用户数月排行榜(AppAlbumUserCountRankMonth)表控制层
 *
 * @author jdq
 * @since 2021-07-06 17:00:41
 */
@RestController
@RequestMapping("/report/app_album_user_count_rank_month")
public class AppAlbumUserCountRankMonthController extends ChartController<AppAlbumUserCountRankMonth> {
    /**
     * 服务对象
     */
    @Resource
    private AppAlbumUserCountRankMonthService appAlbumUserCountRankMonthService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/album_user_count_rank/month");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId, String startDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppAlbumUserCountRankMonth> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] st = t.split("-");
            qw.eq("y",st[0]);
            qw.eq("m",st[1]);
        });
        if(StringUtils.isEmpty(orderby))
            qw.orderByAsc("parent_column_id,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = appAlbumUserCountRankMonthService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAlbumUserCountRankMonth> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appAlbumUserCountRankMonthService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId,Integer rankLimit, String countFields,String countFieldTypes, String startDate) {
        QueryWrapper<AppAlbumUserCountRankMonth> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] st = t.split("-");
            qw.eq("y",st[0]);
            qw.eq("m",st[1]);
        });
        qw.orderByAsc("parent_column_id,rank");
        return super.rankData(appAlbumUserCountRankMonthService,qw,rankLimit,countFields,countFieldTypes);
    }
}
