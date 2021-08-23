package com.ppfuns.report.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.NoappAreaVisitCountMonth;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 所有专区按周统计信息 服务类
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
public interface INoappAreaVisitCountMonthService extends IService<NoappAreaVisitCountMonth> {
    List areaList(QueryWrapper<NoappAreaVisitCountMonth> qw);

    IPage areaPage(Page<NoappAreaVisitCountMonth> pg, QueryWrapper<NoappAreaVisitCountMonth> qw);
}
