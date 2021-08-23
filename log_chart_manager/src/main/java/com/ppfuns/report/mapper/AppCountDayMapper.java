package com.ppfuns.report.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppCountDay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专区日统计(AppCountDay)表数据库访问层
 *
 * @author jdq
 * @since 2021-07-12 17:27:52
 */
public interface AppCountDayMapper extends BaseMapper<AppCountDay> {


    List<AppCountDay> selectSourceData(@Param("startDate") String startDate, @Param("endDate")String endDate);

    List<AppCountDay> selectTotalVisitUserCount(@Param("endDate") String startDate);

    List mergeList(@Param("ew")QueryWrapper<AppCountDay> qw, @Param("countFlag") int countFlag);

    IPage mergePage(Page<AppCountDay> pg, @Param("ew")QueryWrapper<AppCountDay> qw, @Param("countFlag") int countFlag);
}
