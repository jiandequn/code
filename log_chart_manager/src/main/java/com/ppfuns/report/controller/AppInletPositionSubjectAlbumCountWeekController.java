package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppInletPositionCountWeek;
import com.ppfuns.report.entity.AppInletPositionSubjectAlbumCountWeek;
import com.ppfuns.report.service.AppInletPositionSubjectAlbumCountWeekService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import com.alibaba.excel.EasyExcel;

/**
 * 入口专题下详情页访问周统计(AppInletPositionSubjectAlbumCountWeek)表控制层
 *
 * @author jdq
 * @since 2021-08-02 16:52:48
 */
@RestController
@RequestMapping("/report/app_inlet_position_subject_album_count_week")
public class AppInletPositionSubjectAlbumCountWeekController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AppInletPositionSubjectAlbumCountWeekService appInletPositionSubjectAlbumCountWeekService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/inlet_position_subject_album_count/week");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String userType,String parentColumnId, String startDate,String endDate,Integer page, Integer limit, String orderby) {
        QueryWrapper<AppInletPositionSubjectAlbumCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppInletPositionSubjectAlbumCountWeek>().formatWeek(qw,startDate,endDate);
        if(StringUtils.isEmpty(orderby))
            qw.orderByAsc("y","w","user_type","parent_column_id");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.appInletPositionSubjectAlbumCountWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppInletPositionSubjectAlbumCountWeek> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(appInletPositionSubjectAlbumCountWeekService.page(pg, qw));
    }
}
