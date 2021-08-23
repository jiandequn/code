package com.ppfuns.report.service.impl;

import com.ppfuns.report.entity.TableColumnInfo;
import com.ppfuns.report.mapper.TableColumnInfoMapper;
import com.ppfuns.report.service.ITableColumnInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-20
 */
@Service
public class TableColumnInfoServiceImpl extends ServiceImpl<TableColumnInfoMapper, TableColumnInfo> implements ITableColumnInfoService {

    @Override
    public List<TableColumnInfo> getSelectTableField(Integer tableId) {
        return this.baseMapper.getSelectTableField(tableId);
    }
}
