package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppAreaVisitCountWeek;
import com.ppfuns.report.entity.AppSearchAlbumRankWeek;
import com.ppfuns.report.entity.base.CountBean;
import com.ppfuns.report.service.IAppSearchAlbumRankWeekService;
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
 * 专区搜索专辑按周排行榜 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/report/app-search-album-rank-week")
public class AppSearchAlbumRankWeekController extends ChartController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppSearchAlbumRankWeekController.class);
    @Autowired
    private IAppSearchAlbumRankWeekService iAppSearchAlbumRankWeekService;
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/search_album_rank_week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId, String week, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppSearchAlbumRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("parent_column_id,count");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iAppSearchAlbumRankWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppSearchAlbumRankWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppSearchAlbumRankWeekService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId, String week,Integer rankLimit ,String countFields,String countFieldTypes) {
        QueryWrapper<AppSearchAlbumRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        qw.orderByDesc("parent_column_id,count");
//        List<AppSearchAlbumRankWeek> userList =this.iAppSearchAlbumRankWeekService.list(qw);
        return this.rankData(iAppSearchAlbumRankWeekService,qw,rankLimit,countFields,countFieldTypes,new CountBean("搜索次数","count","count","次","搜索次数"));
//        List categories = new ArrayList<>(userList.size());
//        List data = new ArrayList<>(userList.size());
//        userList.forEach(s->{
//            categories.add(s.getAlbumId()+"&"+s.getAlbumName());
//            data.add(s.getCount());
//        });
//        Map<String,Object> serieMap = new HashMap<>();
//        serieMap.put("name","专辑收藏数");
//        serieMap.put("data",data);
//        List series = new ArrayList<>();
//        series.add(serieMap);
//        Map<String,Object> result = new HashMap<>();
//        result.put("series",series);
//        result.put("categories",categories);
//        return result;
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String parentColumnId, String week)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppSearchAlbumRankWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(week).filter(s -> !s.isEmpty()).ifPresent(t->{
            String[] weeks =week.split("-");
            qw.eq("y",weeks[0]).eq("w",weeks[1]);
        });
        qw.orderByDesc("parent_column_id,count");
        List<AppSearchAlbumRankWeek> userList =this.iAppSearchAlbumRankWeekService.list(qw);
        EasyExcel.write(out, AppAreaVisitCountWeek.class).sheet("新增用户统计").doWrite(userList);
    }
}
