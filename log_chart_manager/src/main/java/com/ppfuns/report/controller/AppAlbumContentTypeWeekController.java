package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AlbumContentType;
import com.ppfuns.report.entity.AppAlbumContentTypeWeek;
import com.ppfuns.report.entity.AppAreaVisitCountDay;
import com.ppfuns.report.service.AlbumContentTypeService;
import com.ppfuns.report.service.IAppAlbumContentTypeWeekService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 专辑类型周统计 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/report/app-album-content-type-week")
public class AppAlbumContentTypeWeekController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppAlbumContentTypeWeekController.class);
    @Autowired
    private IAppAlbumContentTypeWeekService iAppAlbumContentTypeWeekService;
    @Autowired
    private AlbumContentTypeService albumContentTypeService;
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/album_content_type_week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId, String startDate, String endDate, String contentTypes, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppAlbumContentTypeWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(contentTypes).filter(s -> !s.isEmpty()).ifPresent(t->{
            qw.in("content_type",contentTypes.split(","));
        });
        new QueryConditionsUtils<AppAlbumContentTypeWeek>().formatWeek(qw,startDate,endDate);
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.iAppAlbumContentTypeWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAlbumContentTypeWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppAlbumContentTypeWeekService.page(pg,qw));
    }

    /**
     * 曲线图
     * @param userType
     * @param parentColumnId
     * @param countType
     * @param contentTypes
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType, String parentColumnId, Integer countType, String contentTypes, String startDate, String endDate) {
        QueryWrapper<AppAlbumContentTypeWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        qw.orderByAsc("y","w");
        QueryWrapper<AlbumContentType> qwe = new QueryWrapper();
        if(!StringUtils.isEmpty(contentTypes)){
            qwe.in("content_type",contentTypes.split(","));
        }
        List<AlbumContentType> albumContentTypes = albumContentTypeService.list(qwe);
        //获取时间数据
        List<String> contentTypeList = albumContentTypes.stream().map(s -> s.getContentType()).collect(Collectors.toList());
        QueryWrapper<AppAlbumContentTypeWeek> dateqw = qw.clone();
        dateqw.in("content_type",contentTypeList);
        dateqw.groupBy("y,w");
        dateqw.orderByAsc("y","w");
        dateqw.select("y","w");
        List<AppAlbumContentTypeWeek> datelist = iAppAlbumContentTypeWeekService.list(dateqw);
        int dateLen= datelist.size();
        Map<String,Object> result = new HashMap<>();
        List categories = datelist.stream().map(s->s.getY()+"-"+s.getW()).collect(Collectors.toList());
        List series = Collections.synchronizedList(new ArrayList<Object>());
        albumContentTypes.parallelStream().forEach(s->{
            QueryWrapper<AppAlbumContentTypeWeek> qc = qw.clone();
            qc.eq("content_type",s.getContentType());
            List<AppAlbumContentTypeWeek> list = this.iAppAlbumContentTypeWeekService.list(qc);
            List data = new ArrayList();
            Map<String,Object> serieMap = new HashMap<>();
            serieMap.put("data",data);
            serieMap.put("name",s.getContentTypeName());
            int offset=0;
            for(int i=0;i<dateLen;i++){
                AppAlbumContentTypeWeek c_date = datelist.get(i);
                if(i-offset < list.size()){
                    AppAlbumContentTypeWeek c = list.get(i-offset);
                    if(StringUtils.equals(c.getW(),c_date.getW()) &&
                            StringUtils.equals(c.getY(),c_date.getY())){
                        data.add(countType==0?c.getUserCount():countType==1?c.getPlayCount():c.getDuration());
                    }else{
                        offset=offset-1;
                        data.add(0);
                    }
                }else{
                    data.add(0);
                }
            }
            series.add(serieMap);
        });
        result.put("series",series);
        result.put("categories",categories);
        result.put("yAxisTitle",countType==0?"播放用户数":countType==1?"播放次数":"播放时长");
        return result;
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String parentColumnId, @RequestParam(defaultValue = "week") String dateType, String startDate, String endDate, String year, String month, String week, String areaFlag)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppAlbumContentTypeWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        new QueryConditionsUtils<AppAlbumContentTypeWeek>().formatWeek(qw,startDate,endDate);
        List<AppAlbumContentTypeWeek> userList =this.iAppAlbumContentTypeWeekService.list(qw);
        EasyExcel.write(out, AppAlbumContentTypeWeek.class).sheet("专区内专辑类型周统计").doWrite(userList);
    }
}
