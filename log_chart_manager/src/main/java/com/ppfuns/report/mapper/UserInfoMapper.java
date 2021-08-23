package com.ppfuns.report.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountDay;
import com.ppfuns.report.entity.AppAreaVisitCountWeek;
import com.ppfuns.report.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ppfuns.report.entity.base.VennEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("SELECT\n" +
            "\tYEAR (\n" +
            "\t\tADDDATE(\n" +
            "\t\t\tcreate_time,\n" +
            "\t\t\t6 - WEEKDAY(create_time)\n" +
            "\t\t)\n" +
            "\t) as y,\n" +
            "\tWEEKOFYEAR(create_time) w,\n" +
            "\tcount(DISTINCT user_type,user_id) AS pageUserCount \n" +
            "FROM\n" +
            "\tuser_info\n" +
            " ${ew.customSqlSegment}\n" +
            "group by y,w;")
    List<AppAreaVisitCountWeek> getIncreaseUserCountChartByWeek(@Param(Constants.WRAPPER)QueryWrapper qw);
    @Select("select u.user_type as userType,u.area as areaCode,u.user_count as pageUserCount,a.area_name as areaName from (select user_type,ifnull(area_code,'') as area,count(distinct user_id) user_count from user_info ${ew.customSqlSegment} group by user_type,area)u" +
            " left join t_area_info a on a.area_no=u.area")
    List<AppAreaVisitCountWeek> selectAreaIncreaseUserCount(@Param(Constants.WRAPPER)QueryWrapper qw);
    @Select("select parent_column_id as name,count(1) as value from user_info ${ew.customSqlSegment}")
    List<VennEntity> getIncreaseUserCountVenn(@Param(Constants.WRAPPER)QueryWrapper qw);
    @Select("select name,count(1) as value from (\n" +
            "select user_id,GROUP_CONCAT(parent_column_id) as name from user_info ${ew.customSqlSegment} group by user_type,user_id HAVING count(1)>1) a group by name;")
    List<VennEntity> getIncreaseUserCountVenn2(@Param(Constants.WRAPPER)QueryWrapper qw);

    List areaUserCountList(@Param(Constants.WRAPPER)QueryWrapper<HashMap> qw,@Param("dateType")String dateType);

    IPage areaUserCountPage(Page<HashMap> pg, @Param(Constants.WRAPPER)QueryWrapper<HashMap> qw,@Param("dateType")String dateType);

    List list2(@Param(Constants.WRAPPER)QueryWrapper<UserInfo> qw);

    IPage page2(Page<UserInfo> pg, @Param(Constants.WRAPPER)QueryWrapper<UserInfo> qw);

    List noAppAreaUserCountList( @Param(Constants.WRAPPER)Map<String, String> params);

    IPage noAppAreaUserCountPage(Page<HashMap> pg,  @Param(Constants.WRAPPER)Map<String, String> params);

    List<AppAreaVisitCountDay> getAreaIncreaseUserCountList(@Param("params")Map qw);

    List<AppAreaVisitCountDay> getTotalAreaVisitUserCount(@Param("params")Map params);
}
