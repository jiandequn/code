package com.ppfuns.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppfuns.dao.ExportTableDao;
import com.ppfuns.dao.TableColumnInfoDao;
import com.ppfuns.entity.ResultPage;
import com.ppfuns.entity.SearchPage;
import com.ppfuns.entity.table.ExportTableEntity;
import com.ppfuns.entity.table.TableColumnInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
public class ExportTableService {
    @Autowired
    private ExportTableDao exportTableDao;
    @Autowired
    private TableColumnInfoDao tableColumnInfoDao;
    @Autowired
    private TableInfoService tableInfoService;
    public List<Map<String, String>> execQuery(String querySql, String tableName) {
        String q = querySql.replaceAll("\\$\\{tableName\\}",tableName);
        return exportTableDao.execQuery(q,tableName);
    }
    public List<Map<String, String>> execQueryByPage(String querySql, String tableName,Integer pageIndex,Integer pageSize) {
        String q = querySql.replaceAll("\\$\\{tableName\\}",tableName);
        q = q.replaceFirst((";[\\s]*$"),"");
        q = q.concat(" limit ").concat(pageIndex+","+pageSize).concat(";");
        return exportTableDao.execQuery(q,tableName);
    }
    public Integer execQueryCount(String querySql, String tableName){
        String q = querySql.replaceAll("\\$\\{tableName\\}",tableName);
        q = q.replaceFirst((";[\\s]*$"),"");
        q = "select count(1) from (".concat(q).concat(") a");
        return exportTableDao.execQueryCount(q);
    }
    public ExportTableEntity getById(Integer id) {
        return exportTableDao.selectById(id);
    }

    public int deleteById(Integer id) {
        tableColumnInfoDao.deleteByExportTableId(id);
        return exportTableDao.deleteById(id);
    }

    public int put(ExportTableEntity tableInfo, String columnInfos) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,TableColumnInfoEntity.class);
                List<TableColumnInfoEntity> list = objectMapper.readValue(columnInfos,javaType);
                if(tableInfo.getId() != null){
                    exportTableDao.update(tableInfo);
                    tableColumnInfoDao.deleteByExportTableId(tableInfo.getId());
                }else{
                    exportTableDao.insert(tableInfo);
                }
                list.forEach(tableColumnInfoEntity -> {
                    tableColumnInfoEntity.setExportTableId(tableInfo.getId());
                    tableColumnInfoEntity.setIsEffective(1);
                    tableColumnInfoDao.insert(tableColumnInfoEntity);
                });
                return 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return -1;
    }

    public ResultPage getList(SearchPage<ExportTableEntity> searchPage) {
        ResultPage<ExportTableEntity> resultPage = new ResultPage<ExportTableEntity>();
        resultPage.setRows(exportTableDao.getList(searchPage));
        resultPage.setTotal(exportTableDao.getCount(searchPage));
        return resultPage;
    }
}
