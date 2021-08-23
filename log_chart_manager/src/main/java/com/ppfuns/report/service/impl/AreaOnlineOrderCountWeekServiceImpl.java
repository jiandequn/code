package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AreaOnlineOrderCountWeekMapper;
import com.ppfuns.report.entity.AreaOnlineOrderCountWeek;
import com.ppfuns.report.service.AreaOnlineOrderCountWeekService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域在线订购周统计(AreaOnlineOrderCountWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-06-02 16:54:59
 */
@Service("areaOnlineOrderCountWeekService")
public class AreaOnlineOrderCountWeekServiceImpl extends ServiceImpl<AreaOnlineOrderCountWeekMapper, AreaOnlineOrderCountWeek> implements AreaOnlineOrderCountWeekService {

    @Override
    public List includeAreaList(QueryWrapper<AreaOnlineOrderCountWeek> qw) {
        return this.baseMapper.includeAreaList(qw);
    }

    @Override
    public IPage includeAreaPage(Page<AreaOnlineOrderCountWeek> pg, QueryWrapper<AreaOnlineOrderCountWeek> qw) {
        return this.baseMapper.includeAreaPage(pg,qw);
    }
}
