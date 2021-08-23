package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AreaOnlineOrderCountDay;
import com.ppfuns.report.service.AreaOnlineOrderCountDayService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * 区域在线订购日统计(AreaOnlineOrderCountDay)表控制层
 *
 * @author jdq
 * @since 2021-06-02 16:47:00
 */
@RestController
@RequestMapping("/report/area_online_order_count_day")
public class AreaOnlineOrderCountDayController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AreaOnlineOrderCountDayService areaOnlineOrderCountDayService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/area_online_order_count/day/show");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String userType, Integer productId, String productName, String thirdProductCode, String areaCodes, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AreaOnlineOrderCountDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(productId).ifPresent(t->qw.eq("product_id",productId));
        Optional.ofNullable(productName).filter(s -> !s.isEmpty()).ifPresent(t->qw.like("product_name",productName));
        Optional.ofNullable(thirdProductCode).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("third_product_code",thirdProductCode));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->qw.in("area_code",areaCodes.split(",")));
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.areaOnlineOrderCountDayService.includeAreaList(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AreaOnlineOrderCountDay> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(areaOnlineOrderCountDayService.includeAreaPage(pg, qw));
    }

    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String userType,Integer productId,String productName,String areaCodes,String thirdProductCode, String startDate, String endDate,String orderby)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2 + ".xlsx");
        QueryWrapper<AreaOnlineOrderCountDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(productId).ifPresent(t->qw.eq("product_id",productId));
        Optional.ofNullable(productName).filter(s -> !s.isEmpty()).ifPresent(t->qw.like("product_name",productName));
        Optional.ofNullable(thirdProductCode).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("third_product_code",thirdProductCode));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->qw.in("area_code",areaCodes.split(",")));
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AreaOnlineOrderCountDay> userList = this.areaOnlineOrderCountDayService.list(qw);
        EasyExcel.write(out, AreaOnlineOrderCountDay.class).sheet("区域在线订购日统计").doWrite(userList);
    }

}
