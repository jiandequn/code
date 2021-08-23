package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AreaOnlineOrderCountDayMapper;
import com.ppfuns.report.entity.AreaOnlineOrderCountDay;
import com.ppfuns.report.service.AreaOnlineOrderCountDayService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域在线订购日统计(AreaOnlineOrderCountDay)表服务实现类
 *
 * @author jdq
 * @since 2021-06-02 16:47:00
 */
@Service("areaOnlineOrderCountDayService")
public class AreaOnlineOrderCountDayServiceImpl extends ServiceImpl<AreaOnlineOrderCountDayMapper, AreaOnlineOrderCountDay> implements AreaOnlineOrderCountDayService {

    @Override
    public List includeAreaList(QueryWrapper<AreaOnlineOrderCountDay> qw) {
        return this.baseMapper.includeAreaList(qw);
    }

    @Override
    public IPage includeAreaPage(Page<AreaOnlineOrderCountDay> pg, QueryWrapper<AreaOnlineOrderCountDay> qw) {
        return this.baseMapper.includeAreaPage(pg,qw);
    }
}
