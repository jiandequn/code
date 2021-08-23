package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppDrawPrizeCountWeek;
import com.ppfuns.report.service.AppDrawPrizeCountWeekService;
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
 * 专区抽奖按周统计(AppDrawPrizeCountWeek)表控制层
 *
 * @author jdq
 * @since 2021-06-02 17:03:32
 */
@RestController
@RequestMapping("/report/app_draw_prize_count_week")
public class AppDrawPrizeCountWeekController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AppDrawPrizeCountWeekService appDrawPrizeCountWeekService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/draw_prize_count_week");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String userType, String parentColumnId, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppDrawPrizeCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        new QueryConditionsUtils<AppDrawPrizeCountWeek>().formatWeek(qw,startDate,endDate);
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.appDrawPrizeCountWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppDrawPrizeCountWeek> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(appDrawPrizeCountWeekService.page(pg, qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String parentColumnId, String userType, String startDate,String endDate, String orderby) {
        QueryWrapper<AppDrawPrizeCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        new QueryConditionsUtils<AppDrawPrizeCountWeek>().formatWeek(qw,startDate,endDate);
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AppDrawPrizeCountWeek> list =this.appDrawPrizeCountWeekService.list(qw);
        List categories = new ArrayList<>(list.size());
        List drawUserCountData = new ArrayList<>(list.size());
        List drawCountData = new ArrayList<>(list.size());
        List drawedUserCountData = new ArrayList<>(list.size());
        List drawedCountData = new ArrayList<>(list.size());
        list.forEach(s->{
            categories.add(s.getY()+"年"+s.getW()+"周");
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
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String userType,String parentColumnId,String startDate, String endDate,String orderby)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2 + ".xlsx");
        QueryWrapper<AppDrawPrizeCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        new QueryConditionsUtils<AppDrawPrizeCountWeek>().formatWeek(qw,startDate,endDate);
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AppDrawPrizeCountWeek> userList = this.appDrawPrizeCountWeekService.list(qw);
        EasyExcel.write(out, AppDrawPrizeCountWeek.class).sheet("专区抽奖按周统计").doWrite(userList);
    }

    /**
     * 分页查询所有数据
     *
     * @param page                  分页对象
     * @param appDrawPrizeCountWeek 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<AppDrawPrizeCountWeek> page, AppDrawPrizeCountWeek appDrawPrizeCountWeek) {
        return success(this.appDrawPrizeCountWeekService.page(page, new QueryWrapper<>(appDrawPrizeCountWeek)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.appDrawPrizeCountWeekService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param appDrawPrizeCountWeek 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody AppDrawPrizeCountWeek appDrawPrizeCountWeek) {
        return success(this.appDrawPrizeCountWeekService.save(appDrawPrizeCountWeek));
    }

    /**
     * 修改数据
     *
     * @param appDrawPrizeCountWeek 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody AppDrawPrizeCountWeek appDrawPrizeCountWeek) {
        return success(this.appDrawPrizeCountWeekService.updateById(appDrawPrizeCountWeek));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.appDrawPrizeCountWeekService.removeByIds(idList));
    }
}
