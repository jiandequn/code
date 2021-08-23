package com.ppfuns.report.service.impl;

import com.ppfuns.report.entity.TableInfo;
import com.ppfuns.report.mapper.TableInfoMapper;
import com.ppfuns.report.service.ITableInfoService;
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
public class TableInfoServiceImpl extends ServiceImpl<TableInfoMapper, TableInfo> implements ITableInfoService {

    @Override
    public List<TableInfo> getAllTableInDb() {
        return this.baseMapper.getAllTableInDb();
    }
}
