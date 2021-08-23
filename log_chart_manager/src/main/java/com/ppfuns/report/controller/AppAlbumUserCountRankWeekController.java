package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppAlbumUserCountRankDay;
import com.ppfuns.report.entity.AppAlbumUserCountRankWeek;
import com.ppfuns.report.service.IAppAlbumUserCountRankWeekService;
import com.ppfuns.report.utils.ChartUtils;
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
 * 专辑播放用户数排行榜 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/report/app_album_user_count_rank_week")
public class AppAlbumUserCountRankWeekController extends ChartController<AppAlbumUserCountRankWeek> {
    private static final Logger logger = LoggerFactory
            .getLogger(AppAlbumUserCountRankWeekController.class);
    @Autowired
    private IAppAlbumUserCountRankWeekService iAppAlbumUserCountRankWeekService;
    @RequestMapping(value = "/toPage1", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/album_user_count_rank_week");
        return mav;
    }
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/album_user_count_rank/week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId, String week, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppAlbumUserCountRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("parent_column_id,user_count");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iAppAlbumUserCountRankWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAlbumUserCountRankWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppAlbumUserCountRankWeekService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId, String week,Integer rankLimit, String countFields,String countFieldTypes) {
        QueryWrapper<AppAlbumUserCountRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        qw.orderByDesc("parent_column_id,user_count");
        return super.rankData(iAppAlbumUserCountRankWeekService,qw,rankLimit,countFields,countFieldTypes);
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String parentColumnId, String week)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppAlbumUserCountRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        qw.orderByDesc("parent_column_id,user_count");
        List<AppAlbumUserCountRankWeek> userList =this.iAppAlbumUserCountRankWeekService.list(qw);
        EasyExcel.write(out, AppAlbumUserCountRankWeek.class).sheet("新增用户统计").doWrite(userList);
    }
}
