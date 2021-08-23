package com.ppfuns.report.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ppfuns.report.entity.NoappCountDay;

import java.util.List;

/**
 * 所有专区日统计(NoappCountDay)表服务接口
 *
 * @author jdq
 * @since 2021-07-12 17:28:09
 */
public interface NoappCountDayService extends IService<NoappCountDay> {

    void gengerateData(String startDate, String endDate);

    List mergeList(QueryWrapper<NoappCountDay> qw, int countFlag);

    IPage mergePage(Page<NoappCountDay> pg, QueryWrapper<NoappCountDay> qw, int countFlag);
}
