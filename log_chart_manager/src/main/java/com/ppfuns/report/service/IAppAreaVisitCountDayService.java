package com.ppfuns.report.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountDay;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 专区按天统计信息 服务类
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-19
 */
public interface IAppAreaVisitCountDayService extends IService<AppAreaVisitCountDay> {

    List areaList(QueryWrapper<AppAreaVisitCountDay> qw);

    IPage areaPage(Page<AppAreaVisitCountDay> pg, QueryWrapper<AppAreaVisitCountDay> qw);

    void gengerateData(String sDate, String eDate);
}
