package com.ppfuns.controller;

import com.ppfuns.entity.ResultPage;
import com.ppfuns.entity.SearchDate;
import com.ppfuns.entity.SearchPage;
import com.ppfuns.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局用户统计数据来源
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/17
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @RequestMapping("/global/user/list")
    public ResultPage getGlobalUserList(SearchPage<SearchDate> searchPage,SearchDate searchDate){
         searchDate.setTableName("global_user");
         searchPage.setT(searchDate);
         return reportService.list(searchPage);
    }

}
