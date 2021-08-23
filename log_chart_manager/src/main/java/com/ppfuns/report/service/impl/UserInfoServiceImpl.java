package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountDay;
import com.ppfuns.report.entity.AppAreaVisitCountWeek;
import com.ppfuns.report.entity.UserInfo;
import com.ppfuns.report.entity.base.VennEntity;
import com.ppfuns.report.mapper.UserInfoMapper;
import com.ppfuns.report.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Override
    public List<AppAreaVisitCountWeek> getIncreaseUserCountChartByWeek(QueryWrapper qw) {
        return this.baseMapper.getIncreaseUserCountChartByWeek(qw);
    }

    @Override
    public List<AppAreaVisitCountWeek> selectAreaIncreaseUserCount(QueryWrapper qw) {
        return this.baseMapper.selectAreaIncreaseUserCount(qw);
    }

    @Override
    public List<VennEntity> getIncreaseUserCountVenn(QueryWrapper qw) {
        return this.baseMapper.getIncreaseUserCountVenn(qw);
    }

    @Override
    public List<VennEntity> getIncreaseUserCountVenn2(QueryWrapper qw) {
        return this.baseMapper.getIncreaseUserCountVenn2(qw);
    }

    @Override
    public List areaUserCountList(QueryWrapper<HashMap> qw,String dateType) {
        return baseMapper.areaUserCountList(qw,dateType);
    }

    @Override
    public IPage areaUserCountPage(Page<HashMap> pg, QueryWrapper<HashMap> qw,String dateType) {
        return baseMapper.areaUserCountPage(pg,qw,dateType);
    }

    @Override
    public List list2(QueryWrapper<UserInfo> qw) {
        return baseMapper.list2(qw);
    }

    @Override
    public IPage page2(Page<UserInfo> pg, QueryWrapper<UserInfo> qw) {
        return baseMapper.page2(pg,qw);
    }

    @Override
    public List noAppAreaUserCountList(Map<String, String> params) {
        return baseMapper.noAppAreaUserCountList(params);
    }

    @Override
    public IPage noAppAreaUserCountPage(Page<HashMap> pg, Map<String, String> params) {
        return baseMapper.noAppAreaUserCountPage(pg,params);
    }

    @Override
    public List<AppAreaVisitCountDay> getAreaIncreaseUserCountList(Map qw) {
        return baseMapper.getAreaIncreaseUserCountList(qw);
    }

    @Override
    public List<AppAreaVisitCountDay> selectTotalAreaVisitUserCount(Map<String, String> params) {
        return baseMapper.getTotalAreaVisitUserCount(params);
    }
}
