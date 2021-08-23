package com.ppfuns.report.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AreaOnlineOrderCountMonth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域在线订购周统计(AreaOnlineOrderCountMonth)表数据库访问层
 *
 * @author jdq
 * @since 2021-06-02 16:52:38
 */
public interface AreaOnlineOrderCountMonthMapper extends BaseMapper<AreaOnlineOrderCountMonth> {
    List includeAreaList(@Param("ew") QueryWrapper<AreaOnlineOrderCountMonth> qw);

    IPage includeAreaPage(Page<AreaOnlineOrderCountMonth> pg, @Param("ew")QueryWrapper<AreaOnlineOrderCountMonth> qw);
}
