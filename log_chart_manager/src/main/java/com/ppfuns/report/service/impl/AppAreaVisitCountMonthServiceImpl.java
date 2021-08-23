package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountMonth;
import com.ppfuns.report.mapper.AppAreaVisitCountMonthMapper;
import com.ppfuns.report.service.IAppAreaVisitCountMonthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 专区按月统计信息 服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
@Service
public class AppAreaVisitCountMonthServiceImpl extends ServiceImpl<AppAreaVisitCountMonthMapper, AppAreaVisitCountMonth> implements IAppAreaVisitCountMonthService {

    @Override
    public List areaList(QueryWrapper<AppAreaVisitCountMonth> qw) {
        return this.baseMapper.areaList(qw);
    }

    @Override
    public IPage areaPage(Page<AppAreaVisitCountMonth> pg, QueryWrapper<AppAreaVisitCountMonth> qw) {
        return this.baseMapper.areaPage(pg,qw);
    }
}
