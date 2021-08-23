package com.ppfuns.report.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AreaOnlineOrderCountDay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域在线订购日统计(AreaOnlineOrderCountDay)表数据库访问层
 *
 * @author jdq
 * @since 2021-06-02 16:47:00
 */
public interface AreaOnlineOrderCountDayMapper extends BaseMapper<AreaOnlineOrderCountDay> {
    List includeAreaList(@Param("ew")QueryWrapper<AreaOnlineOrderCountDay> qw);

    IPage includeAreaPage(Page<AreaOnlineOrderCountDay> pg, @Param("ew")QueryWrapper<AreaOnlineOrderCountDay> qw);
}
