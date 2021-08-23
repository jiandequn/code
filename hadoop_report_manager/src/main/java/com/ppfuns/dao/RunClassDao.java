package com.ppfuns.dao;

import com.ppfuns.entity.SearchDate;
import com.ppfuns.entity.SearchPage;
import com.ppfuns.entity.table.RunClassEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/12/27
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public interface RunClassDao{
    List<RunClassEntity> select(RunClassEntity runClassEntity);

    RunClassEntity selectById(@Param("id") Integer id);

    int del(@Param("id")Integer id);

    int update(RunClassEntity runClassEntity);

    int insert(RunClassEntity runClassEntity);

    List<RunClassEntity> getList(SearchPage<SearchDate> searchPage);

    Integer getCount(SearchPage<SearchDate> searchPage);

    List<RunClassEntity> getCanRun(@Param("ids")String ids);
}
