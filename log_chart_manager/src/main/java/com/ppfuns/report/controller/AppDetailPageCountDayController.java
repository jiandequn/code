package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppDetailPageCountDay;
import com.ppfuns.report.service.AppDetailPageCountDayService;
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
 * 按日统计详情访问(AppDetailPageCountDay)表控制层
 *
 * @author jdq
 * @since 2021-06-15 18:23:59
 */
@RestController
@RequestMapping("/report/app_detail_page_count_day")
public class AppDetailPageCountDayController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AppDetailPageCountDayService appDetailPageCountDayService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/detail_page_count_day");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String userType,String parentColumnId, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppDetailPageCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.appDetailPageCountDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppDetailPageCountDay> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(appDetailPageCountDayService.page(pg, qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType,String parentColumnId, String startDate, String endDate) {
        QueryWrapper<AppDetailPageCountDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppDetailPageCountDay>().formatWeek(qw,startDate,endDate);
        List<AppDetailPageCountDay> userList =this.appDetailPageCountDayService.list(qw);
        List categories = new ArrayList<>(userList.size());
        List userCountdata = new ArrayList<>(userList.size());
        List visitCountdata = new ArrayList<>(userList.size());
        userList.forEach(s->{
            categories.add(s.gettDate());
            userCountdata.add(s.getUserCount());
            visitCountdata.add(s.getVisitCount());
        });
        Map<String,Object> serieMap = new HashMap<>();
        serieMap.put("name","用户数");
        serieMap.put("type","column");
        serieMap.put("yAxis",1);
        serieMap.put("data",userCountdata);

        Map<String,Object> serieMap2 = new HashMap<>();
        serieMap2.put("name","点击次数");
        serieMap.put("type","spline");
        serieMap2.put("data",visitCountdata);
        List series = new ArrayList<>();
        series.add(serieMap);
        series.add(serieMap2);
        Map<String,Object> result = new HashMap<>();
        result.put("series",series);
        result.put("categories",categories);
        return result;
    }

    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response,String userType, String orderby)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2 + ".xlsx");
        QueryWrapper<AppDetailPageCountDay> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AppDetailPageCountDay> userList = this.appDetailPageCountDayService.list(qw);
        EasyExcel.write(out, AppDetailPageCountDay.class).sheet("按日统计详情访问").doWrite(userList);
    }

    /**
     * 分页查询所有数据
     *
     * @param page                  分页对象
     * @param appDetailPageCountDay 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<AppDetailPageCountDay> page, AppDetailPageCountDay appDetailPageCountDay) {
        return success(this.appDetailPageCountDayService.page(page, new QueryWrapper<>(appDetailPageCountDay)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.appDetailPageCountDayService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param appDetailPageCountDay 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody AppDetailPageCountDay appDetailPageCountDay) {
        return success(this.appDetailPageCountDayService.save(appDetailPageCountDay));
    }

    /**
     * 修改数据
     *
     * @param appDetailPageCountDay 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody AppDetailPageCountDay appDetailPageCountDay) {
        return success(this.appDetailPageCountDayService.updateById(appDetailPageCountDay));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.appDetailPageCountDayService.removeByIds(idList));
    }
}
