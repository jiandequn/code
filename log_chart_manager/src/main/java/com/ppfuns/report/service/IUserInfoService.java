package com.ppfuns.report.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountDay;
import com.ppfuns.report.entity.AppAreaVisitCountWeek;
import com.ppfuns.report.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ppfuns.report.entity.base.VennEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
public interface IUserInfoService extends IService<UserInfo> {

    List<AppAreaVisitCountWeek> getIncreaseUserCountChartByWeek(QueryWrapper qw);

    List<AppAreaVisitCountWeek> selectAreaIncreaseUserCount(QueryWrapper qw);

    List<VennEntity> getIncreaseUserCountVenn(QueryWrapper qw);

    List<VennEntity> getIncreaseUserCountVenn2(QueryWrapper qw);

    List areaUserCountList(QueryWrapper<HashMap> qw,String dateType);

    IPage areaUserCountPage(Page<HashMap> pg, QueryWrapper<HashMap> qw,String dateType);

    List list2(QueryWrapper<UserInfo> qw);

    IPage page2(Page<UserInfo> pg, QueryWrapper<UserInfo> qw);

    List noAppAreaUserCountList(Map<String, String> params);

    IPage noAppAreaUserCountPage(Page<HashMap> pg, Map<String, String> params);

    List<AppAreaVisitCountDay> getAreaIncreaseUserCountList(Map queryWrapper);

    List<AppAreaVisitCountDay> selectTotalAreaVisitUserCount(Map<String, String> params);
}
