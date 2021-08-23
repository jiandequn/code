package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppDrawPrizeCountDay;
import com.ppfuns.report.service.AppDrawPrizeCountDayService;
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
 * 专区抽奖按日统计(AppDrawPrizeCountDay)表控制层
 *
 * @author jdq
 * @since 2021-06-02 16:58:44
 */
@RestController
@RequestMapping("/report/app_draw_prize_count_day")
public class AppDrawPrizeCountDayController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AppDrawPrizeCountDayService appDrawPrizeCountDayService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/draw_prize_count_day");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String parentColumnId, String userType, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppDrawPrizeCountDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.appDrawPrizeCountDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppDrawPrizeCountDay> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(appDrawPrizeCountDayService.page(pg, qw));
    }

    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response,String parentColumnId,String userType, String startDate, String endDate, String orderby)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2 + ".xlsx");
        QueryWrapper<AppDrawPrizeCountDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AppDrawPrizeCountDay> userList = this.appDrawPrizeCountDayService.list(qw);
        EasyExcel.write(out, AppDrawPrizeCountDay.class).sheet("专区抽奖按日统计").doWrite(userList);
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId,String userType, String startDate, String endDate,String orderby) {
        QueryWrapper<AppDrawPrizeCountDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AppDrawPrizeCountDay> list =this.appDrawPrizeCountDayService.list(qw);
        List categories = new ArrayList<>(list.size());
        List drawUserCountData = new ArrayList<>(list.size());
        List drawCountData = new ArrayList<>(list.size());
        List drawedUserCountData = new ArrayList<>(list.size());
        List drawedCountData = new ArrayList<>(list.size());
        list.forEach(s->{
            categories.add(s.gettDate());
            drawUserCountData.add(s.getDrawUserCount());
            drawCountData.add(s.getDrawCount());
            drawedUserCountData.add(s.getDrawedUserCount());
            drawedCountData.add(s.getDrawedCount());
        });
        Map<String,Object> serieMap = new HashMap<>();
        serieMap.put("name","抽奖用户数");
        serieMap.put("data",drawUserCountData);

        Map<String,Object> serieMap2 = new HashMap<>();
        serieMap2.put("name","抽奖次数");
        serieMap2.put("data",drawCountData);
        Map<String,Object> serieMap3 = new HashMap<>();
        serieMap3.put("name","中奖用户数");
        serieMap3.put("data",drawedUserCountData);
        Map<String,Object> serieMap4 = new HashMap<>();
        serieMap4.put("name","中奖次数");
        serieMap4.put("data",drawedCountData);

        List series = new ArrayList<>();
        series.add(serieMap);
        series.add(serieMap2);
        series.add(serieMap3);
        series.add(serieMap4);
        Map<String,Object> result = new HashMap<>();
        result.put("series",series);
        result.put("categories",categories);
        return result;
    }
    /**
     * 分页查询所有数据
     *
     * @param page                 分页对象
     * @param appDrawPrizeCountDay 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<AppDrawPrizeCountDay> page, AppDrawPrizeCountDay appDrawPrizeCountDay) {
        return success(this.appDrawPrizeCountDayService.page(page, new QueryWrapper<>(appDrawPrizeCountDay)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.appDrawPrizeCountDayService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param appDrawPrizeCountDay 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody AppDrawPrizeCountDay appDrawPrizeCountDay) {
        return success(this.appDrawPrizeCountDayService.save(appDrawPrizeCountDay));
    }

    /**
     * 修改数据
     *
     * @param appDrawPrizeCountDay 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody AppDrawPrizeCountDay appDrawPrizeCountDay) {
        return success(this.appDrawPrizeCountDayService.updateById(appDrawPrizeCountDay));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.appDrawPrizeCountDayService.removeByIds(idList));
    }
}
