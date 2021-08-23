package com.ppfuns.controller;

import com.ppfuns.entity.*;
import com.ppfuns.entity.table.ExportTableEntity;
import com.ppfuns.service.ExportTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/29
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/export/table")
public class ExportTableController {
    @Autowired
    private ExportTableService exportTableService;

    @RequestMapping(method = RequestMethod.GET)
    public ExportTableEntity get(Integer id){
        return exportTableService.getById(id);
    }
    @RequestMapping("/list")
    public ResultPage getList(SearchPage<ExportTableEntity> searchPage, ExportTableEntity searchDate){
        searchPage.setT(searchDate);
        return exportTableService.getList(searchPage);
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public ResultData delete(Integer id){
        int i =exportTableService.deleteById(id);
        if(i == 1) return new ResultData();
        return new ResultData("0","删除失败(请检查字段数据是否删除)");
    }
    @RequestMapping(method = {RequestMethod.PUT,RequestMethod.POST})
    public ResultData put(ExportTableEntity tableInfo,String columnInfos){
        int i =exportTableService.put(tableInfo,columnInfos);
        if(i == 1) return new ResultData();
        return new ResultData("0","新增失败");
    }
}
