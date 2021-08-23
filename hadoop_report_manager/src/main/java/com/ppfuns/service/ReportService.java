package com.ppfuns.service;

import com.ppfuns.dao.GlobalUserDao;
import com.ppfuns.entity.GlobalUserEntity;
import com.ppfuns.entity.ResultPage;
import com.ppfuns.entity.SearchDate;
import com.ppfuns.entity.SearchPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/17
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ReportService {
    @Autowired
    private GlobalUserDao globalUserDao;
    public ResultPage<GlobalUserEntity> list(SearchPage<SearchDate> searchPage){
        ResultPage<GlobalUserEntity> resultPage = new ResultPage<GlobalUserEntity>();
        resultPage.setRows(this.globalUserDao.getList(searchPage));
        resultPage.setTotal(this.globalUserDao.getCount(searchPage));
        resultPage.setFooter(this.globalUserDao.getFooter(searchPage));
        return resultPage;
    }
}
