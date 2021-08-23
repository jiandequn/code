package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AreaOnlineOrderCountMonthMapper;
import com.ppfuns.report.entity.AreaOnlineOrderCountMonth;
import com.ppfuns.report.service.AreaOnlineOrderCountMonthService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域在线订购周统计(AreaOnlineOrderCountMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-06-02 16:52:38
 */
@Service("areaOnlineOrderCountMonthService")
public class AreaOnlineOrderCountMonthServiceImpl extends ServiceImpl<AreaOnlineOrderCountMonthMapper, AreaOnlineOrderCountMonth> implements AreaOnlineOrderCountMonthService {

    @Override
    public List includeAreaList(QueryWrapper<AreaOnlineOrderCountMonth> qw) {
        return this.baseMapper.includeAreaList(qw);
    }

    @Override
    public IPage includeAreaPage(Page<AreaOnlineOrderCountMonth> pg, QueryWrapper<AreaOnlineOrderCountMonth> qw) {
        return this.baseMapper.includeAreaPage(pg,qw);
    }
}
