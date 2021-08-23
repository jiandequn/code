package com.ppfuns.report.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.NoappAreaVisitCountWeek;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 所有专区按周统计信息 Mapper 接口
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
public interface NoappAreaVisitCountWeekMapper extends BaseMapper<NoappAreaVisitCountWeek> {
    List areaList(@Param("ew") QueryWrapper<NoappAreaVisitCountWeek> qw);

    IPage areaPage(Page<NoappAreaVisitCountWeek> pg, @Param("ew") QueryWrapper<NoappAreaVisitCountWeek> qw);
}
