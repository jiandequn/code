package com.ppfuns.report.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountWeek;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 专区按周统计信息 服务类
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
public interface IAppAreaVisitCountWeekService extends IService<AppAreaVisitCountWeek> {

    List areaList(QueryWrapper<AppAreaVisitCountWeek> qw);

    IPage areaPage(Page<AppAreaVisitCountWeek> pg, QueryWrapper<AppAreaVisitCountWeek> qw);
}
