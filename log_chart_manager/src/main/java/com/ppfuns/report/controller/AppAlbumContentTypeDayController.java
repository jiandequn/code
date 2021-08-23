package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AlbumContentType;
import com.ppfuns.report.entity.AppAlbumContentTypeDay;
import com.ppfuns.report.entity.AppAlbumContentTypeWeek;
import com.ppfuns.report.service.AlbumContentTypeService;
import com.ppfuns.report.service.IAppAlbumContentTypeDayService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
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
 * 专辑类型日统计 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/report/app-album-content-type-day")
public class AppAlbumContentTypeDayController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppAlbumContentTypeDayController.class);
    @Autowired
    private IAppAlbumContentTypeDayService iAppAlbumContentTypeDayService;
    @Autowired
    private AlbumContentTypeService albumContentTypeService;
    /**
     * 日访问数据页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/album_content_type_day");
        return mav;
    }
    @RequestMapping("list")
    public ResultPage getList(String parentColumnId, String contentTypes, String startDate, String endDate, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppAlbumContentTypeDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(contentTypes).filter(s -> !s.isEmpty()).ifPresent(t->{
            qw.in("content_type",contentTypes.split(","));
        });
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.iAppAlbumContentTypeDayService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAlbumContentTypeDay> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppAlbumContentTypeDayService.page(pg,qw));
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
        QueryWrapper<AppAlbumContentTypeDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        qw.orderByAsc("t_date");
        QueryWrapper<AlbumContentType> qwe = new QueryWrapper();
        if(!StringUtils.isEmpty(contentTypes)){
            qwe.in("content_type",contentTypes.split(","));
        }
        List<AlbumContentType> albumContentTypes = albumContentTypeService.list(qwe);
        //获取时间数据
        List<String> contentTypeList = albumContentTypes.stream().map(s -> s.getContentType()).collect(Collectors.toList());
        QueryWrapper<AppAlbumContentTypeDay> dateqw = qw.clone();
        dateqw.in("content_type",contentTypeList);
        dateqw.groupBy("t_date");
        dateqw.orderByAsc("t_date");
        dateqw.select("t_date");
        List<AppAlbumContentTypeDay> datelist = iAppAlbumContentTypeDayService.list(dateqw);
        int dateLen= datelist.size();
        Map<String,Object> result = new HashMap<>();
        List categories = datelist.stream().map(s->s.gettDate()).collect(Collectors.toList());
        List series = Collections.synchronizedList(new ArrayList<Object>());
        albumContentTypes.parallelStream().forEach(s->{
            QueryWrapper<AppAlbumContentTypeDay> qc = qw.clone();
            qc.eq("content_type",s.getContentType());
            List<AppAlbumContentTypeDay> list = this.iAppAlbumContentTypeDayService.list(qc);
            List data = new ArrayList();
            Map<String,Object> serieMap = new HashMap<>();
            serieMap.put("data",data);
            serieMap.put("name",s.getContentTypeName());
            int offset=0;
            for(int i=0;i<dateLen;i++){
                AppAlbumContentTypeDay c_date = datelist.get(i);
                if(i-offset < list.size()){
                    AppAlbumContentTypeDay c = list.get(i-offset);
                    if(StringUtils.equals(c.gettDate(),c_date.gettDate())){
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
    @RequestMapping("/chart/list2")
    public Map<String,Object> getChartList2( String parentColumnId, String startDate, String endDate) {
        QueryWrapper<AppAlbumContentTypeDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        List<AppAlbumContentTypeDay> userList =this.iAppAlbumContentTypeDayService.list(qw);
        List<String> xData=new ArrayList<>();//时间
        List<Map> datasets=new ArrayList<>();
        Map<String,Object> data = new HashMap<>();
        data.put("name","");//专辑类型
        data.put("data",new int[5]);//专辑类型
        data.put("unit","个数");
        data.put("type","area");
        data.put("valueDecimals",0);
        Map<String,Object> res = new HashMap<>();
        res.put("xData",xData);
        res.put("datasets",datasets);
        return res;
//        List categories = new ArrayList<>(userList.size());
//        List data = new ArrayList<>(userList.size());
//        List playCountData = new ArrayList<>(userList.size());
//        List durationData = new ArrayList<>(userList.size());
//        userList.forEach(s->{
//            categories.add(s.gettDate());
//            data.add(s.getUserCount());
//            playCountData.add(s.getPlayCount());
//            durationData.add(s.getDuration()/3600);
//        });
//        Map<String,Object> serieMap = new HashMap<>();
//        serieMap.put("name","播放用户数");
//        serieMap.put("data",data);
//        Map<String,Object> serieMap3 = new HashMap<>();
//        serieMap3.put("name","点播次数");
//        serieMap3.put("data",playCountData);
//        Map<String,Object> serieMap4 = new HashMap<>();
//        serieMap4.put("name","播放时长");
//        serieMap4.put("data",durationData);
//
//        List series = new ArrayList<>();
//        series.add(serieMap);
//        series.add(serieMap3);
//        series.add(serieMap4);
//        Map<String,Object> result = new HashMap<>();
//        result.put("series",series);
//        result.put("categories",categories);
//        return result;
    }
    @GetMapping("chart/export")
    public void chartExport(String fileName, HttpServletResponse response, String parentColumnId, String startDate, String endDate)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppAlbumContentTypeDay> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        List<AppAlbumContentTypeDay> userList =this.iAppAlbumContentTypeDayService.list(qw);
        EasyExcel.write(out, AppAlbumContentTypeDay.class).sheet("专区内专辑类型日统计").doWrite(userList);
    }
}
