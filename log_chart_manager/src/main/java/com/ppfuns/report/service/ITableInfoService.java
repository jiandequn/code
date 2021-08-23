package com.ppfuns.report.service;

import com.ppfuns.report.entity.TableInfo;
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
public interface ITableInfoService extends IService<TableInfo> {

    List<TableInfo> getAllTableInDb();
}
