package com.ppfuns.service;

import com.ppfuns.dao.TableColumnInfoDao;
import com.ppfuns.dao.TableInfoDao;
import com.ppfuns.entity.ResultPage;
import com.ppfuns.entity.SearchDate;
import com.ppfuns.entity.SearchPage;
import com.ppfuns.entity.base.TableInfo;
import com.ppfuns.entity.table.TableColumnInfoEntity;
import com.ppfuns.utils.EntityProperties;
import com.ppfuns.utils.SqlTableEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/28
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TableInfoService {
    @Autowired
    private EntityProperties entityProperties;
    @Autowired
    private TableInfoDao tableInfoDao;
    @Autowired
    private TableColumnInfoDao tableColumnInfoDao;
    public String getTableDesc(String tableName) {
        return tableInfoDao.getTableDesc(tableName);
    }

    public List<TableColumnInfoEntity> getTableFieldDesc(String tableName) {
        return this.tableInfoDao.getTableFieldDesc(entityProperties.getDatabase(),tableName);
    }

    public List<Map<String, String>> getDataFromTable(String tableName) {
        return tableInfoDao.getDataFromTable(tableName);
    }

    public ResultPage getDownloadList(SearchPage<SearchDate> searchPage) {
        ResultPage<TableInfo> resultPage = new ResultPage<TableInfo>();
        System.out.println(entityProperties);
        List<TableInfo> list = tableInfoDao.selectAllTables(entityProperties.getDatabase());
        //检查tableName是否可以重现
        List<TableInfo> rlist = new ArrayList<>();
        SqlTableEnum[] values = SqlTableEnum.values();
        list.forEach(tableInfo -> {
            for(SqlTableEnum sqlTableEnum:values){
                if(tableInfo.getTableName().contains(sqlTableEnum.getTableName())){
                    TableInfo t =tableInfoDao.get(sqlTableEnum.getTableName());
                    if(t != null && t.getIsEffective() == 1){
                        tableInfo.setId(t.getId());
                        tableInfo.setUpdateSql(t.getUpdateSql());
                        rlist.add(tableInfo);
                    }
                }
            }
        });
        resultPage.setRows(rlist);
        resultPage.setTotal(rlist.size());
        return resultPage;
    }
    public TableInfo get(String alisTableName) {
        String tableName = SqlTableEnum.isExist(alisTableName);
        TableInfo t = tableInfoDao.get(tableName);
        return t;
    }

    public List<TableColumnInfoEntity> getTableColumnsInfoList(TableColumnInfoEntity tableColumnInfoEntity) {
        return tableColumnInfoDao.select(tableColumnInfoEntity);
    }

    public TableInfo getById(Integer tableId) {
        return tableInfoDao.selectById(tableId);
    }

    public int deleteById(Integer tableId) {
        return tableInfoDao.deleteById(tableId);
    }

    public int put(TableInfo tableInfo) {
        if(tableInfo.getId() != null){
            return tableInfoDao.update(tableInfo);
        }
        return tableInfoDao.insert(tableInfo);
    }

    public ResultPage getTableInfoList(SearchPage<SearchDate> searchPage) {
        ResultPage<TableInfo> resultPage = new ResultPage<TableInfo>();
        resultPage.setRows(tableInfoDao.getTableInfoList(searchPage));
        resultPage.setTotal(tableInfoDao.getTableInfoCount(searchPage));
        return resultPage;
    }

    public ResultPage showList(SearchPage<SearchDate> searchPage) {
        ResultPage<TableInfo> resultPage = new ResultPage<TableInfo>();
        List<TableInfo> list = tableInfoDao.selectAllTables(entityProperties.getDatabase());
        //检查tableName是否可以重现
        List<TableInfo> rlist = new ArrayList<>();
        list.forEach(tableInfo -> {
            TableInfo t = this.get(tableInfo.getTableName());
            if(t == null){
                rlist.add(tableInfo);
            }
        });
        resultPage.setRows(rlist);
        resultPage.setTotal(list.size());
        return resultPage;
    }

    public List<TableInfo> select(TableInfo tableInfo) {
        return this.tableInfoDao.select(tableInfo);
    }

    public List<TableColumnInfoEntity> fieldList(Integer tableId) {
        TableInfo tableInfo = tableInfoDao.selectById(tableId);
        String tableName = tableInfo.getTableName();
        return tableInfoDao.getTableFieldDesc(entityProperties.getDatabase(),tableName);
    }

    public String dropTableForDownload(String tableName) {
        for(SqlTableEnum sqlTableEnum:SqlTableEnum.values()){
            if(tableName.contains(sqlTableEnum.getTableName()) && !tableName.equals(sqlTableEnum.getTableName())){
                 tableInfoDao.dropTableForDownload(tableName);
                return "删除成功";
            }
        }
        return "不可删除";
    }
}
