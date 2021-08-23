package com.ppfuns.service;

import com.ppfuns.dao.BackupLogDao;
import com.ppfuns.dao.TableInfoDao;
import com.ppfuns.entity.base.TableInfo;
import com.ppfuns.utils.SqlTableEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/24
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BackupLogService {
    @Autowired
    private BackupLogDao backupLogDao;
    @Autowired
    private TableInfoDao tableInfoDao;
    public int backupTableData(String tableName) {
        //检查是否有该表
        SqlTableEnum tableEnum = SqlTableEnum.getTable(tableName);
        if(tableEnum == null) return -2;
         String checkTableName = backupLogDao.checkTable(tableEnum.getTableName());
        if(StringUtils.isEmpty(checkTableName)){  //如果不存在创建表
            return -3;
        }
        HashMap<String,String> t = backupLogDao.selectTableStruct(tableEnum.getTableName());
        String tableSql = t.get("Create Table");
        String renameTableName = backupLogDao.selectTableDate(tableEnum.getTableName());
        if(StringUtils.isEmpty(renameTableName)){
            return 1;
        }
        backupLogDao.dropReNameTableName(renameTableName);
        backupLogDao.createTable(tableName,renameTableName,tableSql);
        return 1;
    }

    public int updateTableData(String tableName) {
        String alisTableName = SqlTableEnum.isExist(tableName);
//        if(tableEnum == null) return -2;
        TableInfo tableInfo = tableInfoDao.get(alisTableName);
        if(tableInfo == null) return -2;
        //检查是否需要修改数据
        if(!StringUtils.isEmpty(tableInfo.getUpdateSql())){
            tableInfoDao.execUpdate(tableInfo.getUpdateSql(),tableName);
            return 1;
        }
        return 0;
    }
}
