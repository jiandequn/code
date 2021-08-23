package com.ppfuns.dao;

import com.ppfuns.entity.ParentColumnEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 清理池
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/7
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public interface CleanLogDao {
    /**
     * 得到待清理的用户池
     * @return
     */
    List<String> getCleanUserIdList();

    List<ParentColumnEntity> getParentColumnIdList();

    Integer clearDataWeek(@Param("tableName") String tableName,@Param("week") Integer week);
}
