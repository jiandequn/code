package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppPrizeDetailCountWeek;
import com.ppfuns.report.service.AppPrizeDetailCountWeekService;
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
 * 专区奖品的抽奖按周统计(AppPrizeDetailCountWeek)表控制层
 *
 * @author jdq
 * @since 2021-06-15 18:48:35
 */
@RestController
@RequestMapping("/report/app_prize_detail_count_week")
public class AppPrizeDetailCountWeekController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AppPrizeDetailCountWeekService appPrizeDetailCountWeekService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/prize_detail_count_week");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String parentColumnId, String userType, String startDate, String endDate, String prizeId, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppPrizeDetailCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        new QueryConditionsUtils<AppPrizeDetailCountWeek>().formatWeek(qw,startDate,endDate);
        Optional.ofNullable(prizeId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("prize_id",prizeId));
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.appPrizeDetailCountWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppPrizeDetailCountWeek> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(appPrizeDetailCountWeekService.page(pg, qw));
    }

    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String orderby)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2 + ".xlsx");
        QueryWrapper<AppPrizeDetailCountWeek> qw = new QueryWrapper();
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AppPrizeDetailCountWeek> userList = this.appPrizeDetailCountWeekService.list(qw);
        EasyExcel.write(out, AppPrizeDetailCountWeek.class).sheet("专区奖品的抽奖按周统计").doWrite(userList);
    }

    /**
     * 分页查询所有数据
     *
     * @param page                    分页对象
     * @param appPrizeDetailCountWeek 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<AppPrizeDetailCountWeek> page, AppPrizeDetailCountWeek appPrizeDetailCountWeek) {
        return success(this.appPrizeDetailCountWeekService.page(page, new QueryWrapper<>(appPrizeDetailCountWeek)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.appPrizeDetailCountWeekService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param appPrizeDetailCountWeek 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody AppPrizeDetailCountWeek appPrizeDetailCountWeek) {
        return success(this.appPrizeDetailCountWeekService.save(appPrizeDetailCountWeek));
    }

    /**
     * 修改数据
     *
     * @param appPrizeDetailCountWeek 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody AppPrizeDetailCountWeek appPrizeDetailCountWeek) {
        return success(this.appPrizeDetailCountWeekService.updateById(appPrizeDetailCountWeek));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.appPrizeDetailCountWeekService.removeByIds(idList));
    }
}
