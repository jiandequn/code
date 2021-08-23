package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ppfuns.report.entity.TableColumnInfo;
import com.ppfuns.report.service.ITableColumnInfoService;
import com.ppfuns.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-20
 */
@RestController
@RequestMapping("/report/table-column-info")
public class TableColumnInfoController {
    @Autowired
    private ITableColumnInfoService iTableColumnInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public TableColumnInfo get(Integer id){
        return iTableColumnInfoService.getById(id);
    }
    @RequestMapping("/select")
    public List<TableColumnInfo> getList(Integer exportTableId){
        QueryWrapper<TableColumnInfo> qw = new QueryWrapper<>();
        Optional.ofNullable(exportTableId).ifPresent(t->qw.eq("export_table_id",exportTableId));
        qw.orderByAsc("seq");
        return iTableColumnInfoService.list(qw);
    }

    @RequestMapping(method = {RequestMethod.PUT,RequestMethod.POST})
    public ResultData put(TableColumnInfo entity){
        if(iTableColumnInfoService.save(entity)) return new ResultData();
        return new ResultData("0","保存成功");
    }
    @RequestMapping("/getSelectTableField")
    public List<TableColumnInfo> getSelectTableField(Integer tableId){
        return iTableColumnInfoService.getSelectTableField(tableId);
    }
}
