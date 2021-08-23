package com.ppfuns.report.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AlbumContentType;
import com.ppfuns.report.entity.AppAlbumContentTypeMonth;
import com.ppfuns.report.entity.AppAlbumContentTypeWeek;
import com.ppfuns.report.service.AlbumContentTypeService;
import com.ppfuns.report.service.IAppAlbumContentTypeMonthService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
 * 专辑类型月统计 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/report/app-album-content-type-month")
public class AppAlbumContentTypeMonthController {
    private static final Logger logger = LoggerFactory
            .getLogger(AppAlbumContentTypeMonthController.class);
    @Autowired
    private IAppAlbumContentTypeMonthService iAppAlbumContentTypeMonthService;
    @Autowired
    private AlbumContentTypeService albumContentTypeService;
    /**
     * 周访问数据页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/album_content_type_month");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId, String startDate, String contentTypes, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppAlbumContentTypeMonth> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        if(!StringUtils.isEmpty(contentTypes)){
            qw.in("content_type",contentTypes.split(","));
        }
        new QueryConditionsUtils().formatMonth(qw,startDate);
        QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.iAppAlbumContentTypeMonthService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppAlbumContentTypeMonth> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iAppAlbumContentTypeMonthService.page(pg,qw));
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
        QueryWrapper<AppAlbumContentTypeMonth> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(startDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.ge("t_date",startDate));
        Optional.ofNullable(endDate).filter(s -> !s.isEmpty()).ifPresent(t->qw.le("t_date",endDate));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        qw.orderByAsc("y","m");
        QueryWrapper<AlbumContentType> qwe = new QueryWrapper();
        if(!StringUtils.isEmpty(contentTypes)){
            qwe.in("content_type",contentTypes.split(","));
        }
        List<AlbumContentType> albumContentTypes = albumContentTypeService.list(qwe);
        //获取时间数据
        List<String> contentTypeList = albumContentTypes.stream().map(s -> s.getContentType()).collect(Collectors.toList());
        QueryWrapper<AppAlbumContentTypeMonth> dateqw = qw.clone();
        dateqw.in("content_type",contentTypeList);
        dateqw.groupBy("y,m");
        dateqw.orderByAsc("y","m");
        dateqw.select("y","m");
        List<AppAlbumContentTypeMonth> datelist = iAppAlbumContentTypeMonthService.list(dateqw);
        int dateLen= datelist.size();
        Map<String,Object> result = new HashMap<>();
        List categories = datelist.stream().map(s->s.getY()+"-"+s.getM()).collect(Collectors.toList());
        List series = Collections.synchronizedList(new ArrayList<Object>());
        albumContentTypes.parallelStream().forEach(s->{
            QueryWrapper<AppAlbumContentTypeMonth> qc = qw.clone();
            qc.eq("content_type",s.getContentType());
            List<AppAlbumContentTypeMonth> list = this.iAppAlbumContentTypeMonthService.list(qc);
            List data = new ArrayList();
            Map<String,Object> serieMap = new HashMap<>();
            serieMap.put("data",data);
            serieMap.put("name",s.getContentTypeName());
            int offset=0;
            for(int i=0;i<dateLen;i++){
                AppAlbumContentTypeMonth c_date = datelist.get(i);
                if(i-offset < list.size()){
                    AppAlbumContentTypeMonth c = list.get(i-offset);
                    if(StringUtils.equals(c.getM(),c_date.getM()) &&
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
    public void chartExport(String fileName, HttpServletResponse response, String parentColumnId, String startDate)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName2 = new String(fileName.getBytes(), "iso-8859-1");
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName2+".xlsx");
        QueryWrapper<AppAlbumContentTypeMonth> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        new QueryConditionsUtils().formatMonth(qw,startDate);
        List<AppAlbumContentTypeMonth> userList =this.iAppAlbumContentTypeMonthService.list(qw);
        EasyExcel.write(out, AppAlbumContentTypeMonth.class).sheet("新增用户统计").doWrite(userList);
    }
}
