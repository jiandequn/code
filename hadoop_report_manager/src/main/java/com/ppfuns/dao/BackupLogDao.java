package com.ppfuns.dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/17
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public interface BackupLogDao {

    String checkTable(@Param("tableName")String tableName);

    String createTable(@Param("tableName")String tableName,@Param("renameTableName")String renameTableName, @Param("tableSql")String tableSql);

    String selectTableDate(@Param("tableName")String tableName);

    HashMap<String,String> selectTableStruct(@Param("tableName")String tableName);

    String dropReNameTableName(@Param("renameTableName") String renameTableName);
}
