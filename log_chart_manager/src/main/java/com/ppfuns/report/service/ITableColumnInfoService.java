package com.ppfuns.report.service;

import com.ppfuns.report.entity.TableColumnInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-20
 */
public interface ITableColumnInfoService extends IService<TableColumnInfo> {

    List<TableColumnInfo> getSelectTableField(Integer tableId);
}
