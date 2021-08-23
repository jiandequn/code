package com.ppfuns.controller;

import com.ppfuns.entity.ResultData;
import com.ppfuns.entity.table.TableColumnInfoEntity;
import com.ppfuns.service.TableColumnInfoService;
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
@RequestMapping("/table/column/info")
public class TableColumnInfoController {
    @Autowired
    private TableColumnInfoService tableColumnInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public TableColumnInfoEntity get(Integer id){
        return tableColumnInfoService.getById(id);
    }
    @RequestMapping("/select")
    public List<TableColumnInfoEntity> getList(TableColumnInfoEntity tableColumnInfoEntity){
        return tableColumnInfoService.select(tableColumnInfoEntity);
    }
    @RequestMapping(method = {RequestMethod.PUT,RequestMethod.POST})
    public ResultData put(TableColumnInfoEntity tableInfo){
        int i =tableColumnInfoService.put(tableInfo);
        if(i == 1) return new ResultData();
        return new ResultData("0","删除失败");
    }
}
