package com.ppfuns.report.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountMonth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 专区按月统计信息 Mapper 接口
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
public interface AppAreaVisitCountMonthMapper extends BaseMapper<AppAreaVisitCountMonth> {
    List areaList(@Param("ew") QueryWrapper<AppAreaVisitCountMonth> qw);

    IPage areaPage(Page<AppAreaVisitCountMonth> pg,@Param("ew")  QueryWrapper<AppAreaVisitCountMonth> qw);
}
