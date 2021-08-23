package com.ppfuns.report.mapper;

import com.ppfuns.report.entity.ExportTableInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 导出table维护信息 Mapper 接口
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-20
 */
public interface ExportTableInfoMapper extends BaseMapper<ExportTableInfo> {
    @Select("${querySql}")
    List<Map<String,String>> execQuery(@Param("querySql")String querySql,@Param("tableName") String tableName);
    @Select("SELECT  * FROM ${tableName}")
    List<Map<String,String>> getDataFromTable(@Param("tableName") String tableName);
    @Select("${querySql}")
    int execQueryCount(@Param("querySql")String querySql);
}
