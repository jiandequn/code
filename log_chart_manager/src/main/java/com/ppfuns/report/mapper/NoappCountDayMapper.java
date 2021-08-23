package com.ppfuns.report.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppCountDay;
import com.ppfuns.report.entity.NoappCountDay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 所有专区日统计(NoappCountDay)表数据库访问层
 *
 * @author jdq
 * @since 2021-07-12 17:28:09
 */
public interface NoappCountDayMapper extends BaseMapper<NoappCountDay> {

    List<NoappCountDay> selectSourceData(@Param("startDate") String startDate, @Param("endDate")String endDate);

    List<NoappCountDay> selectTotalVisitUserCount(@Param("endDate") String startDate);

    IPage mergePage(Page<NoappCountDay> pg, @Param("ew") QueryWrapper<NoappCountDay> qw, @Param("countFlag") int countFlag);

    List mergeList(@Param("ew") QueryWrapper<NoappCountDay> qw,@Param("countFlag") int countFlag);
}
