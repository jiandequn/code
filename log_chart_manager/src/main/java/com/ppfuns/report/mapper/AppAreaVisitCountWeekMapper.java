package com.ppfuns.report.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountWeek;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 专区按周统计信息 Mapper 接口
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
public interface AppAreaVisitCountWeekMapper extends BaseMapper<AppAreaVisitCountWeek> {
    List areaList(@Param("ew") QueryWrapper<AppAreaVisitCountWeek> qw);

    IPage areaPage(Page<AppAreaVisitCountWeek> pg, @Param("ew") QueryWrapper<AppAreaVisitCountWeek> qw);
}
