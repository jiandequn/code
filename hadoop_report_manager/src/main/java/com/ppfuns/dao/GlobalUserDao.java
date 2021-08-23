package com.ppfuns.dao;

import com.ppfuns.entity.GlobalUserEntity;
import com.ppfuns.entity.SearchDate;
import com.ppfuns.entity.SearchPage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/17
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public interface GlobalUserDao {
    public List<GlobalUserEntity> getList(SearchPage<SearchDate> searchPage);
    public int getCount(SearchPage<SearchDate> searchPage);

    List<GlobalUserEntity> getFooter(SearchPage<SearchDate> searchPage);
}
