package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.controller.base.ChartController;
import com.ppfuns.report.entity.AppBookmarkRank;
import com.ppfuns.report.entity.base.CountBean;
import com.ppfuns.report.service.AppBookmarkRankService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 专区总收藏排行榜(AppBookmarkRank)表控制层
 *
 * @author jdq
 * @since 2021-06-15 18:05:18
 */
@RestController
@RequestMapping("/report/app_bookmark_rank")
public class AppBookmarkRankController extends ChartController {
    /**
     * 服务对象
     */
    @Resource
    private AppBookmarkRankService appBookmarkRankService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/bookmark_rank");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId, String date, Integer page, Integer limit,String orderby) {
        QueryWrapper<AppBookmarkRank> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("parent_column_id,count");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = appBookmarkRankService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppBookmarkRank> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appBookmarkRankService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId, String date,Integer rankLimit ,String countFields,String countFieldTypes) {
        QueryWrapper<AppBookmarkRank> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        qw.orderByDesc("parent_column_id,count");
//        List<AppBookmarkRank> userList =this.appBookmarkRankService.list(qw);
        return this.rankData(appBookmarkRankService,qw,rankLimit,countFields,countFieldTypes,new CountBean("收藏量","count","count","次","收藏量"));
//        List categories = new ArrayList<>(userList.size());
//        List data = new ArrayList<>(userList.size());
//        userList.forEach(s->{
//            categories.add(s.getAlbumId()+"&"+s.getAlbumName());
//            data.add(s.getCount());
//        });
//        Map<String,Object> serieMap = new HashMap<>();
//        serieMap.put("name","专辑累计收藏数");
//        serieMap.put("data",data);
//        List series = new ArrayList<>();
//        series.add(serieMap);
//        Map<String,Object> result = new HashMap<>();
//        result.put("series",series);
//        result.put("categories",categories);
//        return result;
    }
}
