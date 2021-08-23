package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppPrizeDetailCountMonth;
import com.ppfuns.report.service.AppPrizeDetailCountMonthService;
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

import com.alibaba.excel.EasyExcel;

/**
 * 专区奖品的抽奖按月统计(AppPrizeDetailCountMonth)表控制层
 *
 * @author jdq
 * @since 2021-06-15 18:48:18
 */
@RestController
@RequestMapping("/report/app_prize_detail_count_month")
public class AppPrizeDetailCountMonthController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AppPrizeDetailCountMonthService appPrizeDetailCountMonthService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/prize_detail_count_month");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String parentColumnId,String userType, String startDate,String prizeId,Integer page, Integer limit, String orderby) {
        QueryWrapper<AppPrizeDetailCountMonth> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(prizeId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("prize_id",prizeId));
        new QueryConditionsUtils<AppPrizeDetailCountMonth>().formatMonth(qw,startDate);
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.appPrizeDetailCountMonthService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppPrizeDetailCountMonth> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(appPrizeDetailCountMonthService.page(pg, qw));
    }

    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String orderby)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2 + ".xlsx");
        QueryWrapper<AppPrizeDetailCountMonth> qw = new QueryWrapper();
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AppPrizeDetailCountMonth> userList = this.appPrizeDetailCountMonthService.list(qw);
        EasyExcel.write(out, AppPrizeDetailCountMonth.class).sheet("专区奖品的抽奖按月统计").doWrite(userList);
    }

    /**
     * 分页查询所有数据
     *
     * @param page                     分页对象
     * @param appPrizeDetailCountMonth 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<AppPrizeDetailCountMonth> page, AppPrizeDetailCountMonth appPrizeDetailCountMonth) {
        return success(this.appPrizeDetailCountMonthService.page(page, new QueryWrapper<>(appPrizeDetailCountMonth)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.appPrizeDetailCountMonthService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param appPrizeDetailCountMonth 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody AppPrizeDetailCountMonth appPrizeDetailCountMonth) {
        return success(this.appPrizeDetailCountMonthService.save(appPrizeDetailCountMonth));
    }

    /**
     * 修改数据
     *
     * @param appPrizeDetailCountMonth 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody AppPrizeDetailCountMonth appPrizeDetailCountMonth) {
        return success(this.appPrizeDetailCountMonthService.updateById(appPrizeDetailCountMonth));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.appPrizeDetailCountMonthService.removeByIds(idList));
    }
}
