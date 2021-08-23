package com.ppfuns.dao;

import com.ppfuns.entity.SearchDate;
import com.ppfuns.entity.SearchPage;
import com.ppfuns.entity.base.TableInfo;
import com.ppfuns.entity.table.TableColumnInfoEntity;
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
public interface TableInfoDao {
    String getTableDesc(@Param("tableName") String tableName);
    List<TableInfo> selectAllTables(@Param("hadoopMrDb")String hadoopMrDb);

    List<TableColumnInfoEntity> getTableFieldDesc(@Param("hadoopMrDb")String hadoopMrDb,@Param("tableName")String tableName);

    List<Map<String,String>> getDataFromTable(@Param("tableName")String tableName);

    TableInfo get(@Param("tableName")String tableName);

    int execUpdate(@Param("updateSql") String updateSql, @Param("tableName") String tableName);


    TableInfo selectById(@Param("tableId") Integer tableId);

    int deleteById(@Param("tableId")Integer tableId);

    List<TableInfo> getTableInfoList(SearchPage<SearchDate> searchPage);
    Integer getTableInfoCount(SearchPage<SearchDate> searchPage);

    int update(TableInfo tableInfo);

    int insert(TableInfo tableInfo);

    List<TableInfo> select(TableInfo tableInfo);

    String dropTableForDownload(@Param("tableName")String tableName);
}
