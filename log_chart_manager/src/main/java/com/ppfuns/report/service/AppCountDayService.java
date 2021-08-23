package com.ppfuns.report.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ppfuns.report.entity.AppCountDay;

import java.util.List;

/**
 * 专区日统计(AppCountDay)表服务接口
 *
 * @author jdq
 * @since 2021-07-12 17:27:52
 */
public interface AppCountDayService extends IService<AppCountDay>  {

    void gengerateData(String startDate, String endDate);

    List mergeList(QueryWrapper<AppCountDay> qw,int countFlag);

    IPage mergePage(Page<AppCountDay> pg, QueryWrapper<AppCountDay> qw,int countFlag);
}
