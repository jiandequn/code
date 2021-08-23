package com.ppfuns.report.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AreaOnlineOrderCountWeek;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域在线订购周统计(AreaOnlineOrderCountWeek)表数据库访问层
 *
 * @author jdq
 * @since 2021-06-02 16:54:59
 */
public interface AreaOnlineOrderCountWeekMapper extends BaseMapper<AreaOnlineOrderCountWeek> {
    List includeAreaList(@Param("ew") QueryWrapper<AreaOnlineOrderCountWeek> qw);

    IPage includeAreaPage(Page<AreaOnlineOrderCountWeek> pg, @Param("ew")QueryWrapper<AreaOnlineOrderCountWeek> qw);
}
