package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.NoappAreaVisitCountWeek;
import com.ppfuns.report.service.INoappAreaVisitCountWeekService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 所有专区按周统计信息 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/report/noapp-area-visit-count-week")
public class NoappAreaVisitCountWeekController {
    private static final Logger logger = LoggerFactory
            .getLogger(NoappAreaVisitCountWeekController.class);
    @Autowired
    private INoappAreaVisitCountWeekService iNoappAreaVisitCountWeekService;
    /**
     * 周访问数据页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/noapp/visit_count_week");
        return mav;
    }
    @RequestMapping(value = "/toPage2", method = RequestMethod.GET)
    public ModelAndView toPage2() {
        logger.debug("区域访问页！");
        ModelAndView mav = new ModelAndView("/report/noapp/area_visit_count/week/show");
        return mav;
    }
    @RequestMapping("area/list")
    public ResultPage getList2(String userType,String startDate, String endDate, String areaCodes, Integer page, Integer limit, String orderby) {
        QueryWrapper<NoappAreaVisitCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        Optional.ofNullable(areaCodes).filter(s -> !s.isEmpty()).ifPresent(t->{
            qw.in("area_code",areaCodes.split(","));
        });
        new QueryConditionsUtils<NoappAreaVisitCountWeek>().formatWeek(qw,startDate,endDate);
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = iNoappAreaVisitCountWeekService.areaList(qw);
            return new ResultPage(list.size(), list);
        }
        Page<NoappAreaVisitCountWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iNoappAreaVisitCountWeekService.areaPage(pg,qw));
    }
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List list() {
        List list= this.iNoappAreaVisitCountWeekService.list();
        return list;
    }
    @RequestMapping("noArea/list")
    public ResultPage getList(String userType,String startDate, String endDate, Integer page, Integer limit,String orderby) {
        QueryWrapper<NoappAreaVisitCountWeek> qw = new QueryWrapper();
        qw.select("y","w","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        new QueryConditionsUtils<NoappAreaVisitCountWeek>().formatWeek(qw,startDate,endDate);
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        qw.groupBy("y,w,user_type");
        if(page ==null || limit == null){
            List list = iNoappAreaVisitCountWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<NoappAreaVisitCountWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iNoappAreaVisitCountWeekService.page(pg,qw));
    }
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType, String startDate, String endDate, String year, String month, String week, String areaFlag) {
        QueryWrapper<NoappAreaVisitCountWeek> qw = new QueryWrapper();
        qw.select("y","w","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        new QueryConditionsUtils<NoappAreaVisitCountWeek>().formatWeek(qw,startDate,endDate);
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        qw.groupBy("y","w","user_type");
        List<NoappAreaVisitCountWeek> userList =this.iNoappAreaVisitCountWeekService.list(qw);
        List categories = new ArrayList<>(userList.size());
        List data = new ArrayList<>(userList.size());
        List playUserData = new ArrayList<>(userList.size());
        List playCountData = new ArrayList<>(userList.size());
        List durationData = new ArrayList<>(userList.size());
        userList.forEach(s->{
            categories.add(s.getY()+"年"+s.getW()+"周");
            data.add(s.getPageUserCount());
            playUserData.add(s.getPlayUserCount());
            playCountData.add(s.getPlayCount());
            durationData.add(s.getDuration()/3600);
        });
        Map<String,Object> serieMap = new HashMap<>();
        serieMap.put("name","访问用户数");
        serieMap.put("data",data);

        Map<String,Object> serieMap2 = new HashMap<>();
        serieMap2.put("name","播放用户数");
        serieMap2.put("data",playUserData);
        Map<String,Object> serieMap3 = new HashMap<>();
        serieMap3.put("name","点播次数");
        serieMap3.put("data",playCountData);
        Map<String,Object> serieMap4 = new HashMap<>();
        serieMap4.put("name","播放时长");
        serieMap4.put("data",durationData);

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
    public void chartExport(String fileName, HttpServletResponse response,String userType, String startDate, String endDate, String year, String month, String week, String areaFlag)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<NoappAreaVisitCountWeek> qw = new QueryWrapper();
        qw.select("y","w","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
        new QueryConditionsUtils<NoappAreaVisitCountWeek>().formatWeek(qw,startDate,endDate);
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        qw.groupBy("y","w","user_type");
        List<NoappAreaVisitCountWeek> userList =this.iNoappAreaVisitCountWeekService.list(qw);
        EasyExcel.write(out, NoappAreaVisitCountWeek.class).sheet("新增用户统计").doWrite(userList);
    }
}
