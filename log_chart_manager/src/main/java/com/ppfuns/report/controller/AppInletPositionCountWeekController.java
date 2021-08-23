package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppInletPositionCountWeek;
import com.ppfuns.report.service.IAppInletPositionCountWeekService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 用户来源按日统计 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-12-28
 */
@RestController
@RequestMapping("/report/app-inlet-position-count-week")
public class AppInletPositionCountWeekController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppInletPositionCountWeekController.class);
    @Autowired
    private IAppInletPositionCountWeekService iAppInletPositionCountWeekService;
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/inlet_position_count_week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String userType,String parentColumnId, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppInletPositionCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppInletPositionCountWeek>().formatWeek(qw,startDate,endDate);
        if(StringUtils.isEmpty(orderby))
            qw.orderByAsc("y","w","user_type","parent_column_id");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iAppInletPositionCountWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppInletPositionCountWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppInletPositionCountWeekService.page(pg,qw));
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response,String userType,String parentColumnId, @RequestParam(defaultValue = "week") String dateType, String startDate, String endDate, String year, String month, String week, String areaFlag)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppInletPositionCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppInletPositionCountWeek>().formatWeek(qw,startDate,endDate);
        List<AppInletPositionCountWeek> userList =this.iAppInletPositionCountWeekService.list(qw);
        EasyExcel.write(out, AppInletPositionCountWeek.class).sheet("周用户来源位置统计").doWrite(userList);
    }
}
