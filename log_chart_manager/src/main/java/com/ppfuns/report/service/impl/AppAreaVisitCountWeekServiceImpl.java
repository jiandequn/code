package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountWeek;
import com.ppfuns.report.mapper.AppAreaVisitCountWeekMapper;
import com.ppfuns.report.service.IAppAreaVisitCountWeekService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 专区按周统计信息 服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
@Service
public class AppAreaVisitCountWeekServiceImpl extends ServiceImpl<AppAreaVisitCountWeekMapper, AppAreaVisitCountWeek> implements IAppAreaVisitCountWeekService {

    @Override
    public List areaList(QueryWrapper<AppAreaVisitCountWeek> qw) {
        return this.baseMapper.areaList(qw);
    }

    @Override
    public IPage areaPage(Page<AppAreaVisitCountWeek> pg, QueryWrapper<AppAreaVisitCountWeek> qw) {
        return this.baseMapper.areaPage(pg,qw);
    }
}
