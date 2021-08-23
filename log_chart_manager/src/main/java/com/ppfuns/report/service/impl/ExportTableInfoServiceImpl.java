package com.ppfuns.report.service.impl;

import com.ppfuns.report.entity.ExportTableInfo;
import com.ppfuns.report.mapper.ExportTableInfoMapper;
import com.ppfuns.report.service.IExportTableInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 导出table维护信息 服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-20
 */
@Service
public class ExportTableInfoServiceImpl extends ServiceImpl<ExportTableInfoMapper, ExportTableInfo> implements IExportTableInfoService {

    @Override
    public List<Map<String, String>> getDataFromTable(String tableName) {
        return this.baseMapper.getDataFromTable(tableName);
    }

    @Override
    public List<Map<String, String>> execQuery(String querySql, String tableName) {
        String q = querySql.replaceAll("\\$\\{tableName\\}",tableName);
        return this.baseMapper.execQuery(q,tableName);
    }

    @Override
    public int execQueryCount(String querySql, String tableName) {
        String q = querySql.replaceAll("\\$\\{tableName\\}",tableName);
        q = q.replaceFirst((";[\\s]*$"),"");
        q = "select count(1) from (".concat(q).concat(") a");
        return baseMapper.execQueryCount(q);
    }

    @Override
    public List<Map<String, String>> execQueryByPage(String querySql, String tableName, int pageIndex, Integer pageSize) {
        String q = querySql.replaceAll("\\$\\{tableName\\}",tableName);
        q = q.replaceFirst((";[\\s]*$"),"");
        q = q.concat(" limit ").concat(pageIndex+","+pageSize).concat(";");
        return this.baseMapper.execQuery(q,tableName);
    }
}
