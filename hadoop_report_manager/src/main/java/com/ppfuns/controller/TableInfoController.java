package com.ppfuns.controller;

import com.ppfuns.entity.ResultData;
import com.ppfuns.entity.ResultPage;
import com.ppfuns.entity.SearchDate;
import com.ppfuns.entity.SearchPage;
import com.ppfuns.entity.base.TableInfo;
import com.ppfuns.entity.table.TableColumnInfoEntity;
import com.ppfuns.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/table/info")
public class TableInfoController {
    @Autowired
    private TableInfoService tableInfoService;

    @RequestMapping("/download/list")
    public ResultPage getDownloadList(SearchPage<SearchDate> searchPage, SearchDate searchDate) {
        searchDate.setTableName("global_user");
        searchPage.setT(searchDate);
        return tableInfoService.getDownloadList(searchPage);
    }
    @RequestMapping("/download/drop/{tableName}")
    public ResultData dropTableForDownload(@PathVariable String tableName) {
        ResultData resultData = new ResultData();
        resultData.setData(tableInfoService.dropTableForDownload(tableName));
        return resultData;
    }
    @RequestMapping("/showList")
    public ResultPage getShowList(SearchPage<SearchDate> searchPage, SearchDate searchDate) {
        searchDate.setTableName("global_user");
        searchPage.setT(searchDate);
        return tableInfoService.showList(searchPage);
    }

    @RequestMapping(method = RequestMethod.GET)
    public TableInfo get(Integer tableId) {
        return tableInfoService.getById(tableId);
    }

    @RequestMapping("/list")
    public ResultPage getList(SearchPage<SearchDate> searchPage, SearchDate searchDate) {
        searchDate.setTableName("global_user");
        searchPage.setT(searchDate);
        return tableInfoService.getTableInfoList(searchPage);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResultData delete(Integer tableId) {
        int i = tableInfoService.deleteById(tableId);
        if (i == 1) return new ResultData();
        return new ResultData("0", "删除失败");
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    public ResultData put(TableInfo tableInfo) {
        int i = tableInfoService.put(tableInfo);
        if (i == 1) return new ResultData();
        return new ResultData("0", "删除失败");
    }

    @RequestMapping("/select")
    public List<TableInfo> select(TableInfo tableInfo) {
        return tableInfoService.select(tableInfo);
    }

    @RequestMapping("/field/list")
    public List<TableColumnInfoEntity> fieldList(Integer tableId) {
        return tableInfoService.fieldList(tableId);
    }

}
