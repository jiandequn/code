package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppContentTypeAlbumPlayCountRankMonth;
import com.ppfuns.report.entity.AppContentTypeAlbumUserCountRankMonth;
import com.ppfuns.report.service.AppContentTypeAlbumUserCountRankMonthService;
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
 * 专区类型下专辑播放用户数月排行榜(AppContentTypeAlbumUserCountRankMonth)表控制层
 *
 * @author jdq
 * @since 2021-07-06 16:58:15
 */
@RestController
@RequestMapping("/report/app_content_type_album_user_count_rank_month")
public class AppContentTypeAlbumUserCountRankMonthController extends ChartController {
    /**
     * 服务对象
     */
    @Resource
    private AppContentTypeAlbumUserCountRankMonthService appContentTypeAlbumUserCountRankMonthService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/content_type_album_user_count_rank/month");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId,String contentType, String startDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppContentTypeAlbumUserCountRankMonth> qw = new QueryWrapper();
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] st = t.split("-");
            qw.eq("y",st[0]);
            qw.eq("m",st[1]);
        });
        if(StringUtils.isEmpty(orderby))
            qw.orderByAsc("parent_column_id,content_type,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = appContentTypeAlbumUserCountRankMonthService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppContentTypeAlbumUserCountRankMonth> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appContentTypeAlbumUserCountRankMonthService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId, String contentType,Integer rankLimit, String countFields,String countFieldTypes, String startDate) {
        QueryWrapper<AppContentTypeAlbumUserCountRankMonth> qw = new QueryWrapper();
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] st = t.split("-");
            qw.eq("y",st[0]);
            qw.eq("m",st[1]);
        });
        qw.orderByAsc("parent_column_id,content_type,rank");
        return super.rankData(appContentTypeAlbumUserCountRankMonthService,qw,rankLimit,countFields,countFieldTypes);
    }
}
