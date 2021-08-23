package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AreaOnlineOrderCountMonth;
import com.ppfuns.report.service.AreaOnlineOrderCountMonthService;
import org.springframework.web.bind.annotation.*;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * 区域在线订购周统计(AreaOnlineOrderCountMonth)表控制层
 *
 * @author jdq
 * @since 2021-06-02 16:52:38
 */
@RestController
@RequestMapping("/report/area_online_order_count_month")
public class AreaOnlineOrderCountMonthController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AreaOnlineOrderCountMonthService areaOnlineOrderCountMonthService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/area_online_order_count/month/show");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String userType,Integer productId,String productName,String thirdProductCode,String areaCodes,String startDate,Integer page, Integer limit, String orderby) {
        QueryWrapper<AreaOnlineOrderCountMonth> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(productId).ifPresent(t->qw.eq("product_id",productId));
        Optional.ofNullable(productName).filter(s -> !s.isEmpty()).ifPresent(t->qw.like("product_name",productName));
        Optional.ofNullable(thirdProductCode).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("third_product_code",thirdProductCode));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->qw.in("area_code",areaCodes.split(",")));
        new QueryConditionsUtils().formatMonth(qw,startDate);
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.areaOnlineOrderCountMonthService.includeAreaList(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AreaOnlineOrderCountMonth> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(areaOnlineOrderCountMonthService.includeAreaPage(pg, qw));
    }

    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response,String userType,Integer productId,String productName,String areaCodes,String thirdProductCode,String startDate, String orderby)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2 + ".xlsx");
        QueryWrapper<AreaOnlineOrderCountMonth> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(productId).ifPresent(t->qw.eq("product_id",productId));
        Optional.ofNullable(productName).filter(s -> !s.isEmpty()).ifPresent(t->qw.like("product_name",productName));
        Optional.ofNullable(thirdProductCode).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("third_product_code",thirdProductCode));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->qw.in("area_code",areaCodes.split(",")));
        new QueryConditionsUtils().formatMonth(qw,startDate);
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AreaOnlineOrderCountMonth> userList = this.areaOnlineOrderCountMonthService.list(qw);
        EasyExcel.write(out, AreaOnlineOrderCountMonth.class).sheet("区域在线订购周统计").doWrite(userList);
    }

    /**
     * 分页查询所有数据
     *
     * @param page                      分页对象
     * @param areaOnlineOrderCountMonth 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<AreaOnlineOrderCountMonth> page, AreaOnlineOrderCountMonth areaOnlineOrderCountMonth) {
        return success(this.areaOnlineOrderCountMonthService.page(page, new QueryWrapper<>(areaOnlineOrderCountMonth)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.areaOnlineOrderCountMonthService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param areaOnlineOrderCountMonth 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody AreaOnlineOrderCountMonth areaOnlineOrderCountMonth) {
        return success(this.areaOnlineOrderCountMonthService.save(areaOnlineOrderCountMonth));
    }

    /**
     * 修改数据
     *
     * @param areaOnlineOrderCountMonth 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody AreaOnlineOrderCountMonth areaOnlineOrderCountMonth) {
        return success(this.areaOnlineOrderCountMonthService.updateById(areaOnlineOrderCountMonth));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.areaOnlineOrderCountMonthService.removeByIds(idList));
    }
}
