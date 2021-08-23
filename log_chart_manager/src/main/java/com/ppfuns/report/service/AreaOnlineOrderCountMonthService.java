package com.ppfuns.report.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ppfuns.report.entity.AreaOnlineOrderCountMonth;

import java.util.List;

/**
 * 区域在线订购周统计(AreaOnlineOrderCountMonth)表服务接口
 *
 * @author jdq
 * @since 2021-06-02 16:52:38
 */
public interface AreaOnlineOrderCountMonthService extends IService<AreaOnlineOrderCountMonth> {

    List includeAreaList(QueryWrapper<AreaOnlineOrderCountMonth> qw);

    IPage includeAreaPage(Page<AreaOnlineOrderCountMonth> pg, QueryWrapper<AreaOnlineOrderCountMonth> qw);
}
