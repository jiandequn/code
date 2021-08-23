package com.ppfuns.controller;

import com.ppfuns.entity.ResultData;
import com.ppfuns.service.BackupLogService;
import com.ppfuns.service.CleanLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/24
 * Time: 10:23
 * To change this template use File | Settings | File Templates.
 */
@RequestMapping("/api")
@RestController
public class ApiController {
    @Autowired
    private BackupLogService backupLogService;
    @Autowired
    private CleanLogService cleanLogService;
    /**
     * 通知处理上周备份日志文件
     * @return
     */
    @RequestMapping("/{tableName}")
    public String clean(@PathVariable String tableName) {
        int i = backupLogService.backupTableData(tableName);
        if(i == 1)return "success";
        return "error";
    }
    /**
     * 通知处理上周备份日志文件
     * @return
     */
    @RequestMapping("update/{tableName}")
    public String updateTableData(@PathVariable String tableName) {
        int i = backupLogService.updateTableData(tableName);
        if(i == 1 || i==0)return "success";
        return "error";
    }
    /**
     * 清理日志数据需要的条件
     * @return
     */
    @RequestMapping("clean/log/condition")
    public ResultData needCleanLogData() {
        ResultData resultData = new ResultData();
        Object t = cleanLogService.getCleanLogData();
        resultData.setData(t);
        return resultData;
    }
    /**
     * 清理日志数据需要的条件
     * @return
     */
    @RequestMapping("clean/data/week")
    public ResultData clearDataWeek(String tableName,Integer week) {
        ResultData resultData = new ResultData();
        Object t = cleanLogService.clearDataWeek(tableName,week);
        resultData.setData(t);
        return resultData;
    }
}
