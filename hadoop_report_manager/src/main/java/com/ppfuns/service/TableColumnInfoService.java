package com.ppfuns.service;

import com.ppfuns.dao.TableColumnInfoDao;
import com.ppfuns.entity.table.TableColumnInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/28
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TableColumnInfoService {
    @Autowired
    private TableColumnInfoDao tableColumnInfoDao;

    public TableColumnInfoEntity getById(Integer tableId) {
        return tableColumnInfoDao.selectById(tableId);
    }

    public int put(TableColumnInfoEntity columnInfoEntity) {
        if(columnInfoEntity.getColumnId() != null){
            return tableColumnInfoDao.update(columnInfoEntity);
        }
        return tableColumnInfoDao.insert(columnInfoEntity);
    }

   public List<TableColumnInfoEntity> select(TableColumnInfoEntity tableColumnInfoEntity){
       return tableColumnInfoDao.select(tableColumnInfoEntity);
   }

}
