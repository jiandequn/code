package com.ppfuns.report.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountMonth;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 专区按月统计信息 服务类
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
public interface IAppAreaVisitCountMonthService extends IService<AppAreaVisitCountMonth> {

    List areaList(QueryWrapper<AppAreaVisitCountMonth> qw);

    IPage areaPage(Page<AppAreaVisitCountMonth> pg, QueryWrapper<AppAreaVisitCountMonth> qw);
}
