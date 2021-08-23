package com.ppfuns.dao;

import com.ppfuns.entity.SearchPage;
import com.ppfuns.entity.table.ExportTableEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/28
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public interface ExportTableDao {

    Integer execQueryCount(@Param("querySql")String querSql);

    int execUpdate(@Param("updateSql") String updateSql, @Param("tableName") String tableName);

    List<Map<String,String>> execQuery(@Param("querySql") String querySql, @Param("tableName") String tableName);

    ExportTableEntity selectById(@Param("id") Integer id);

    int deleteById(@Param("id") Integer id);

    List<ExportTableEntity> getList(SearchPage<ExportTableEntity> searchPage);
    Integer getCount(SearchPage<ExportTableEntity> searchPage);

    int update(ExportTableEntity tableInfo);

    int insert(ExportTableEntity tableInfo);
}
