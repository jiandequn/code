package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppAlbumPlayCountRankDay;
import com.ppfuns.report.entity.AppAlbumPlayCountRankWeek;
import com.ppfuns.report.service.IAppAlbumPlayCountRankDayService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 专区专辑播放次数排行榜 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/report/app-album-play-count-rank-day")
public class AppAlbumPlayCountRankDayController extends ChartController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppAlbumPlayCountRankDayController.class);
    @Autowired
    private IAppAlbumPlayCountRankDayService iAppAlbumPlayCountRankDayService;
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/album_play_count_rank_day");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId, String date, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppAlbumPlayCountRankDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("parent_column_id,play_count");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.iAppAlbumPlayCountRankDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAlbumPlayCountRankDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppAlbumPlayCountRankDayService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId,Integer rankLimit, String countFields,String countFieldTypes, String date) {
        QueryWrapper<AppAlbumPlayCountRankDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        qw.orderByDesc("parent_column_id,play_count");
        return super.rankData(iAppAlbumPlayCountRankDayService,qw,rankLimit,countFields,countFieldTypes);
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String parentColumnId, String y, String w)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppAlbumPlayCountRankDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(y).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("y",y));
        Optional.ofNullable(w).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("w",w));
        qw.orderByDesc("parent_column_id,play_count");
        List<AppAlbumPlayCountRankDay> userList =this.iAppAlbumPlayCountRankDayService.list(qw);
        EasyExcel.write(out, AppAlbumPlayCountRankWeek.class).sheet("新增用户统计").doWrite(userList);
    }
}
