package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.NoappAreaVisitCountWeek;
import com.ppfuns.report.mapper.NoappAreaVisitCountWeekMapper;
import com.ppfuns.report.service.INoappAreaVisitCountWeekService;
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
public class NoappAreaVisitCountWeekServiceImpl extends ServiceImpl<NoappAreaVisitCountWeekMapper, NoappAreaVisitCountWeek> implements INoappAreaVisitCountWeekService {
    @Override
    public List areaList(QueryWrapper<NoappAreaVisitCountWeek> qw) {
        return this.baseMapper.areaList(qw);
    }

    @Override
    public IPage areaPage(Page<NoappAreaVisitCountWeek> pg, QueryWrapper<NoappAreaVisitCountWeek> qw) {
        return this.baseMapper.areaPage(pg,qw);
    }
}
