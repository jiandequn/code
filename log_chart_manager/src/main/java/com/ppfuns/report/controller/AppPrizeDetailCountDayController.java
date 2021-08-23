package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppPrizeDetailCountDay;
import com.ppfuns.report.service.AppPrizeDetailCountDayService;
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
 * 专区奖品的抽奖按日统计(AppPrizeDetailCountDay)表控制层
 *
 * @author jdq
 * @since 2021-06-15 18:47:50
 */
@RestController
@RequestMapping("/report/app_prize_detail_count_day")
public class AppPrizeDetailCountDayController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AppPrizeDetailCountDayService appPrizeDetailCountDayService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/prize_detail_count_day");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String parentColumnId, String userType, String startDate, String endDate, String prizeId, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppPrizeDetailCountDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(prizeId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("prize_id",prizeId));
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.appPrizeDetailCountDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppPrizeDetailCountDay> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(appPrizeDetailCountDayService.page(pg, qw));
    }

    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String orderby)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2 + ".xlsx");
        QueryWrapper<AppPrizeDetailCountDay> qw = new QueryWrapper();
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AppPrizeDetailCountDay> userList = this.appPrizeDetailCountDayService.list(qw);
        EasyExcel.write(out, AppPrizeDetailCountDay.class).sheet("专区奖品的抽奖按日统计").doWrite(userList);
    }

    /**
     * 分页查询所有数据
     *
     * @param page                   分页对象
     * @param appPrizeDetailCountDay 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<AppPrizeDetailCountDay> page, AppPrizeDetailCountDay appPrizeDetailCountDay) {
        return success(this.appPrizeDetailCountDayService.page(page, new QueryWrapper<>(appPrizeDetailCountDay)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.appPrizeDetailCountDayService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param appPrizeDetailCountDay 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody AppPrizeDetailCountDay appPrizeDetailCountDay) {
        return success(this.appPrizeDetailCountDayService.save(appPrizeDetailCountDay));
    }

    /**
     * 修改数据
     *
     * @param appPrizeDetailCountDay 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody AppPrizeDetailCountDay appPrizeDetailCountDay) {
        return success(this.appPrizeDetailCountDayService.updateById(appPrizeDetailCountDay));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.appPrizeDetailCountDayService.removeByIds(idList));
    }
}
