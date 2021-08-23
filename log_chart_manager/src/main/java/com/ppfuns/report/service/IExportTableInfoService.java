package com.ppfuns.report.service;

import com.ppfuns.report.entity.ExportTableInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 导出table维护信息 服务类
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-20
 */
public interface IExportTableInfoService extends IService<ExportTableInfo> {

    List<Map<String,String>> getDataFromTable(String tableName);

    List<Map<String,String>> execQuery(String querySql, String tableName);

    int execQueryCount(String querySql, String tableName);

    List<Map<String,String>> execQueryByPage(String querySql, String tableName, int i, Integer pageSize);
}
