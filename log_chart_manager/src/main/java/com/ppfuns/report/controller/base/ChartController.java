package com.ppfuns.report.controller.base;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ppfuns.report.entity.AppAlbumUserCountRankWeek;
import com.ppfuns.report.entity.NoappCountDay;
import com.ppfuns.report.entity.base.CountBean;
import com.ppfuns.report.utils.ChartUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ChartController<T> extends ApiController {

    public Map<String,Object> rankData(IService iService,QueryWrapper qw, Integer rankLimit, String countFields, String countFieldTypes){
        return rankData(iService,qw,rankLimit,countFields,countFieldTypes,null);
    }

    public Map<String,Object> rankData(IService iService, QueryWrapper qw, Integer rankLimit, String countFields, String countFieldTypes, CountBean countBean){
        List<T> userList = null;
        if(rankLimit == null || rankLimit <1){
            userList = iService.list(qw);
        }else{
            Page pg =new Page(0,rankLimit,true);
            userList = iService.page(pg, qw).getRecords();
        }
        List<String> cateList= null;
        if(iService.getClass().getName().contains("Video")){
            cateList = Arrays.asList("albumName","videoName");
        }else if(iService.getClass().getName().contains("Album")){
//            cateList = Arrays.asList("albumId","albumName");
            cateList = Arrays.asList("albumName");
        }else {
            cateList = Arrays.asList("albumName");
        }
        if(countBean == null){
            return ChartUtils.getChart(userList,cateList,Arrays.asList(countFields.split(",")),Arrays.asList(countFieldTypes.split(",")));
        }
        HashMap<String,CountBean> countBeanMap = new HashMap<>();
        countBeanMap.put("count",countBean);
        return ChartUtils.getChart(userList,cateList,Arrays.asList(countFields.split(",")),Arrays.asList(countFieldTypes.split(",")),countBeanMap);
    }
}
