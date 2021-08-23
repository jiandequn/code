package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AreaOnlineOrderCountWeek;
import com.ppfuns.report.service.AreaOnlineOrderCountWeekService;
import org.springframework.web.bind.annotation.*;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import com.alibaba.excel.EasyExcel;

/**
 * 区域在线订购周统计(AreaOnlineOrderCountWeek)表控制层
 *
 * @author jdq
 * @since 2021-06-02 16:54:59
 */
@RestController
@RequestMapping("/report/area_online_order_count_week")
public class AreaOnlineOrderCountWeekController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AreaOnlineOrderCountWeekService areaOnlineOrderCountWeekService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/area_online_order_count/week/show");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String userType,Integer productId,String productName,String areaCodes,String startDate,String endDate,String thirdProductCode,Integer page, Integer limit, String orderby) {
        QueryWrapper<AreaOnlineOrderCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(productId).ifPresent(t->qw.eq("product_id",productId));
        Optional.ofNullable(productName).filter(s -> !s.isEmpty()).ifPresent(t->qw.like("product_name",productName));
        Optional.ofNullable(thirdProductCode).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("third_product_code",thirdProductCode));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->qw.in("area_code",areaCodes.split(",")));
        new QueryConditionsUtils<AreaOnlineOrderCountWeek>().formatWeek(qw,startDate,endDate);
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.areaOnlineOrderCountWeekService.includeAreaList(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AreaOnlineOrderCountWeek> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(areaOnlineOrderCountWeekService.includeAreaPage(pg, qw));
    }

    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response,String userType,Integer productId,String productName,String areaCodes,String startDate,String endDate,String thirdProductCode, String orderby)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2 + ".xlsx");
        QueryWrapper<AreaOnlineOrderCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(productId).ifPresent(t->qw.eq("product_id",productId));
        Optional.ofNullable(productName).filter(s -> !s.isEmpty()).ifPresent(t->qw.like("product_name",productName));
        Optional.ofNullable(thirdProductCode).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("third_product_code",thirdProductCode));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->qw.in("area_code",areaCodes.split(",")));
        new QueryConditionsUtils<AreaOnlineOrderCountWeek>().formatWeek(qw,startDate,endDate);
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AreaOnlineOrderCountWeek> userList = this.areaOnlineOrderCountWeekService.list(qw);
        EasyExcel.write(out, AreaOnlineOrderCountWeek.class).sheet("区域在线订购周统计").doWrite(userList);
    }

}
