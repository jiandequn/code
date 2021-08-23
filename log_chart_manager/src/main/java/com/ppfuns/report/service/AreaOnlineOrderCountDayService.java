package com.ppfuns.report.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ppfuns.report.entity.AreaOnlineOrderCountDay;

import java.util.List;

/**
 * 区域在线订购日统计(AreaOnlineOrderCountDay)表服务接口
 *
 * @author jdq
 * @since 2021-06-02 16:47:00
 */
public interface AreaOnlineOrderCountDayService extends IService<AreaOnlineOrderCountDay> {

    List includeAreaList(QueryWrapper<AreaOnlineOrderCountDay> qw);

    IPage includeAreaPage(Page<AreaOnlineOrderCountDay> pg, QueryWrapper<AreaOnlineOrderCountDay> qw);
}
