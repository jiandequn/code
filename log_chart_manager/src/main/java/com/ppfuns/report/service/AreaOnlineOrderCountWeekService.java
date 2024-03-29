package com.ppfuns.report.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ppfuns.report.entity.AreaOnlineOrderCountWeek;

import java.util.List;

/**
 * 区域在线订购周统计(AreaOnlineOrderCountWeek)表服务接口
 *
 * @author jdq
 * @since 2021-06-02 16:54:59
 */
public interface AreaOnlineOrderCountWeekService extends IService<AreaOnlineOrderCountWeek> {

    List includeAreaList(QueryWrapper<AreaOnlineOrderCountWeek> qw);

    IPage includeAreaPage(Page<AreaOnlineOrderCountWeek> pg, QueryWrapper<AreaOnlineOrderCountWeek> qw);
}
