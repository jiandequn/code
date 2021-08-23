package com.ppfuns.report.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountDay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 专区按天统计信息 Mapper 接口
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-19
 */
public interface AppAreaVisitCountDayMapper extends BaseMapper<AppAreaVisitCountDay> {
    List areaList(@Param("ew") QueryWrapper<AppAreaVisitCountDay> qw);

    IPage areaPage(Page<AppAreaVisitCountDay> pg, @Param("ew") QueryWrapper<AppAreaVisitCountDay> qw);
}
