package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AlbumContentType;
import com.ppfuns.report.entity.AppAreaVisitCountDay;
import com.ppfuns.report.entity.AppDetailPageAlbumContentTypeCountDay;
import com.ppfuns.report.entity.AppDetailPageAlbumContentTypeCountWeek;
import com.ppfuns.report.entity.base.CountBean;
import com.ppfuns.report.service.AlbumContentTypeService;
import com.ppfuns.report.service.AppDetailPageAlbumContentTypeCountWeekService;
import com.ppfuns.report.utils.ChartUtils;
import com.ppfuns.report.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import com.alibaba.excel.EasyExcel;
import org.thymeleaf.util.StringUtils;

/**
 * 按周统计专辑详情页专辑类型访问(AppDetailPageAlbumContentTypeCountWeek)表控制层
 *
 * @author jdq
 * @since 2021-07-28 16:22:17
 */
@RestController
@RequestMapping("/report/app_detail_page_album_content_type_count_week")
public class AppDetailPageAlbumContentTypeCountWeekController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AppDetailPageAlbumContentTypeCountWeekService appDetailPageAlbumContentTypeCountWeekService;
    @Autowired
    private AlbumContentTypeService albumContentTypeService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/detail_page_album_content_type/week");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(String parentColumnId,String contentType, String date, Integer page, Integer limit, String orderby) {
        QueryWrapper<AppDetailPageAlbumContentTypeCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(date).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("t_date",date));
        Optional.ofNullable(contentType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("content_type",contentType));
        if(org.springframework.util.StringUtils.isEmpty(orderby))
            qw.orderByDesc("t_date,parent_column_id,content_type");
        else QueryConditionsUtils.formatOrderBy(qw,orderby,null);
        if(page ==null || limit == null){
            List list = this.appDetailPageAlbumContentTypeCountWeekService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AppDetailPageAlbumContentTypeCountWeek> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(appDetailPageAlbumContentTypeCountWeekService.page(pg,qw));
    }
    /**
     * 周点播用户数柱状图并与上周对比，示例如下：周详情页各类型访问次数占比；周类型次数/周类型累计次数
     * @param userType
     * @param parentColumnId
     * @param week
     * @param countFields
     * @param countFieldTypes
     * @return
     */
    @RequestMapping("/chart/list")
    public Map<String,Object> getChartList(String userType, String parentColumnId, String week, String countFields, String countFieldTypes) {
        QueryWrapper<AppDetailPageAlbumContentTypeCountWeek> qw = new QueryWrapper();
        Optional.ofNullable(parentColumnId).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("parent_column_id",parentColumnId));
        Optional.ofNullable(userType).filter(s -> !s.isEmpty()).ifPresent(t->qw.eq("user_type",userType));
        if(StringUtils.isEmpty(week)){
            int[] weekAndYear = DateUtils.getWeekAndYear(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).minusWeeks(1).toString());
            week= weekAndYear[0]+"-"+weekAndYear[1];
        }
        LocalDate localMonday = DateUtils.getMondayByWEEKOFYEAR(week);
        LocalDate beforeMonday = localMonday.minusWeeks(1);
        QueryWrapper<AppDetailPageAlbumContentTypeCountWeek> clone = qw.clone();
        clone.eq("y",week.split("-")[0]);
        clone.eq("w",week.split("-")[1]);
        List<AppDetailPageAlbumContentTypeCountWeek> localWeekList =this.appDetailPageAlbumContentTypeCountWeekService.list(clone);
        int[] beforeWeek=DateUtils.getWeekAndYear(beforeMonday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        qw.eq("y",beforeWeek[0]);
        qw.eq("w",beforeWeek[1]);
        List<AppDetailPageAlbumContentTypeCountWeek> beforeWeekList =this.appDetailPageAlbumContentTypeCountWeekService.list(qw);
        BigDecimal localTotal = BigDecimal.valueOf(localWeekList.stream().mapToLong(AppDetailPageAlbumContentTypeCountWeek::getVisitCount).sum());
        BigDecimal beforeTotal = BigDecimal.valueOf(beforeWeekList.stream().mapToLong(AppDetailPageAlbumContentTypeCountWeek::getVisitCount).sum());
        List<AlbumContentType> albumContentTypes = albumContentTypeService.list();
        List<Map<String,Object>> dataList = new ArrayList<>();
        albumContentTypes.forEach(s-> {
            Map<String,Object> map = new HashMap<>();
            map.put("contentTypeName",s.getContentTypeName());
            map.put("contentType",s.getContentType());
            map.put("localVisitCountRatio",BigDecimal.valueOf(0));
            map.put("beforeVisitCountRatio",BigDecimal.valueOf(0));
            localWeekList.forEach(t->{
                if(StringUtils.equals(s.getContentType(),t.getContentType())){
                    map.put("localVisitCountRatio",BigDecimal.valueOf(t.getVisitCount()).divide(localTotal,4,1).multiply(BigDecimal.valueOf(100)));
                    return;
                }
            });
            beforeWeekList.forEach(t->{
                if(StringUtils.equals(s.getContentType(),t.getContentType())){
                    map.put("beforeVisitCountRatio",BigDecimal.valueOf(t.getVisitCount()).divide(beforeTotal,4,1).multiply(BigDecimal.valueOf(100)));
                    return;
                }
            });
            dataList.add(map);
        });
        Map<String, CountBean> countBeanMap = new HashMap<>();
        countBeanMap.put("beforeVisitCountRatio",new CountBean("上周占比","beforeVisitCountRatio","ratio","%","百分比"));
        countBeanMap.put("localVisitCountRatio",new CountBean("本周占比","localVisitCountRatio","ratio","%","百分比"));
        return ChartUtils.getChart(dataList,Arrays.asList("contentTypeName"),Arrays.asList(countFields.split(",")),Arrays.asList(countFieldTypes.split(",")),countBeanMap);
    }

}
