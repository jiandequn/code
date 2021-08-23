package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppDetailPageCount;
import com.ppfuns.report.service.AppDetailPageCountService;
import org.springframework.web.bind.annotation.*;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
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
 * 累计统计详情访问(AppDetailPageCount)表控制层
 *
 * @author jdq
 * @since 2021-06-15 18:23:24
 */
@RestController
@RequestMapping("/report/app_detail_page_count")
public class AppDetailPageCountController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AppDetailPageCountService appDetailPageCountService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/detail_page_count");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(String userType,String parentColumnId,String startDate,String endDate,Integer page, Integer limit, String orderby) {
        QueryWrapper<AppDetailPageCount> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.appDetailPageCountService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppDetailPageCount> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(appDetailPageCountService.page(pg, qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType,String parentColumnId, String startDate, String endDate) {
        QueryWrapper<AppDetailPageCount> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        new QueryConditionsUtils<AppDetailPageCount>().formatWeek(qw,startDate,endDate);
        List<AppDetailPageCount> userList =this.appDetailPageCountService.list(qw);
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
        QueryWrapper<AppDetailPageCount> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        List<AppDetailPageCount> userList = this.appDetailPageCountService.list(qw);
        EasyExcel.write(out, AppDetailPageCount.class).sheet("累计统计详情访问").doWrite(userList);
    }

    /**
     * 分页查询所有数据
     *
     * @param page               分页对象
     * @param appDetailPageCount 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<AppDetailPageCount> page, AppDetailPageCount appDetailPageCount) {
        return success(this.appDetailPageCountService.page(page, new QueryWrapper<>(appDetailPageCount)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.appDetailPageCountService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param appDetailPageCount 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody AppDetailPageCount appDetailPageCount) {
        return success(this.appDetailPageCountService.save(appDetailPageCount));
    }

    /**
     * 修改数据
     *
     * @param appDetailPageCount 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody AppDetailPageCount appDetailPageCount) {
        return success(this.appDetailPageCountService.updateById(appDetailPageCount));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.appDetailPageCountService.removeByIds(idList));
    }
}
