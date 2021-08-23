package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AlbumContentType;
import com.ppfuns.report.entity.AppContentTypeAlbumDurationRankDay;
import com.ppfuns.report.entity.AppDetailPageAlbumContentTypeCountDay;
import com.ppfuns.report.service.AlbumContentTypeService;
import com.ppfuns.report.service.AppDetailPageAlbumContentTypeCountDayService;
import com.ppfuns.report.utils.ChartUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 按日统计专辑详情页专辑类型访问(AppDetailPageAlbumContentTypeCountDay)表控制层
 *
 * @author jdq
 * @since 2021-07-28 16:22:17
 */
@RestController
@RequestMapping("/report/app_detail_page_album_content_type_count_day")
public class AppDetailPageAlbumContentTypeCountDayController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AppDetailPageAlbumContentTypeCountDayService appDetailPageAlbumContentTypeCountDayService;
    @Autowired
    private AlbumContentTypeService albumContentTypeService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/detail_page_album_content_type/day");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId,String contentType, String date, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppDetailPageAlbumContentTypeCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        if(StringUtils.isEmpty(orderby))
            qw.orderByDesc("t_date,parent_column_id,content_type");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.appDetailPageAlbumContentTypeCountDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppDetailPageAlbumContentTypeCountDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appDetailPageAlbumContentTypeCountDayService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId, String countFields, String countFieldTypes, String date, String contentType) {
        QueryWrapper<AppDetailPageAlbumContentTypeCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        qw.orderByDesc("parent_column_id,content_type");
        List<AppDetailPageAlbumContentTypeCountDay> list = this.appDetailPageAlbumContentTypeCountDayService.list(qw);
        List<AlbumContentType> albumContentTypes = albumContentTypeService.list();
        return ChartUtils.getChart(list, Arrays.asList("tDate"),Arrays.asList(countFields.split(",")),Arrays.asList(countFieldTypes.split(",")));
    }
}
