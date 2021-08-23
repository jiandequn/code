package com.ppfuns.controller;

import com.ppfuns.dao.RunClassDao;
import com.ppfuns.entity.ResultData;
import com.ppfuns.entity.ResultPage;
import com.ppfuns.entity.SearchDate;
import com.ppfuns.entity.SearchPage;
import com.ppfuns.entity.table.RunClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/29
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/run/class")
public class RunClassController {
    @Autowired
    private RunClassDao runClassDao;

    @RequestMapping(method = RequestMethod.GET)
    public RunClassEntity get(Integer id){
        return runClassDao.selectById(id);
    }
    @RequestMapping("/list")
    public ResultPage getList(SearchPage<SearchDate> searchPage, SearchDate searchDate) {
        ResultPage<RunClassEntity> resultPage = new ResultPage<RunClassEntity>();
        resultPage.setRows(runClassDao.getList(searchPage));
        resultPage.setTotal(runClassDao.getCount(searchPage));
        return resultPage;
    }
    @RequestMapping("/select")
    public List<RunClassEntity> select(RunClassEntity runClassEntity){
        return runClassDao.select(runClassEntity);
    }
    @RequestMapping(method = {RequestMethod.PUT,RequestMethod.POST})
    public ResultData put(RunClassEntity runClassEntity){
        int i = 0;
        if(runClassEntity.getId() != null){
            i = runClassDao.update(runClassEntity);
        }else {
            i = runClassDao.insert(runClassEntity);
        }

        if(i == 1) return new ResultData();
        return new ResultData("0","删除失败");
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public ResultData delete(Integer id) {
        int i = runClassDao.del(id);
        if (i == 1) return new ResultData();
        return new ResultData("0", "删除失败");
    }
}
