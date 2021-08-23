package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.NoappAreaVisitCountMonth;
import com.ppfuns.report.mapper.NoappAreaVisitCountMonthMapper;
import com.ppfuns.report.service.INoappAreaVisitCountMonthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 所有专区按周统计信息 服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
@Service
public class NoappAreaVisitCountMonthServiceImpl extends ServiceImpl<NoappAreaVisitCountMonthMapper, NoappAreaVisitCountMonth> implements INoappAreaVisitCountMonthService {
    @Override
    public List areaList(QueryWrapper<NoappAreaVisitCountMonth> qw) {
        return this.baseMapper.areaList(qw);
    }

    @Override
    public IPage areaPage(Page<NoappAreaVisitCountMonth> pg, QueryWrapper<NoappAreaVisitCountMonth> qw) {
        return this.baseMapper.areaPage(pg,qw);
    }
}
