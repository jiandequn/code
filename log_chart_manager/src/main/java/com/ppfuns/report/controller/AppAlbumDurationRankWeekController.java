package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppAlbumDurationRankWeek;
import com.ppfuns.report.entity.AppAlbumDurationRankWeek;
import com.ppfuns.report.service.AppAlbumDurationRankWeekService;
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
 * 专区专辑播放时长周排行榜(AppAlbumDurationRankWeek)表控制层
 *
 * @author jdq
 * @since 2021-07-14 15:18:59
 */
@RestController
@RequestMapping("/report/app_album_duration_rank_week")
public class AppAlbumDurationRankWeekController extends ChartController<AppAlbumDurationRankWeek> {
    /**
     * 服务对象
     */
    @Resource
    private AppAlbumDurationRankWeekService appAlbumDurationRankWeekService;
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/album_duration_rank/week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId, String week, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppAlbumDurationRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("parent_column_id,rank");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = appAlbumDurationRankWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAlbumDurationRankWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appAlbumDurationRankWeekService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId,Integer rankLimit, String countFields,String countFieldTypes, String week) {
        QueryWrapper<AppAlbumDurationRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        qw.orderByDesc("parent_column_id,rank");
        return super.rankData(appAlbumDurationRankWeekService,qw,rankLimit,countFields,countFieldTypes);
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String parentColumnId, String week)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppAlbumDurationRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        qw.orderByDesc("parent_column_id,user_count");
        List<AppAlbumDurationRankWeek> userList =this.appAlbumDurationRankWeekService.list(qw);
        EasyExcel.write(out, AppAlbumDurationRankWeek.class).sheet("新增用户统计").doWrite(userList);
    }
}
