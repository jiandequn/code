package com.ppfuns.dao;

import com.ppfuns.entity.table.TableColumnInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/28
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public interface TableColumnInfoDao {
    List<TableColumnInfoEntity> select(TableColumnInfoEntity tableColumnInfoEntity);

    TableColumnInfoEntity selectById(@Param("columnId") Integer id);

    int deleteByExportTableId(@Param("exportTableId")Integer exportTableId);

    int update(TableColumnInfoEntity columnInfoEntity);

    int insert(TableColumnInfoEntity columnInfoEntity);
}
