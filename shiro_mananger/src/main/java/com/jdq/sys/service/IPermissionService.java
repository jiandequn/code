package com.jdq.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdq.sys.entity.SysPermission;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
public interface IPermissionService extends IService<SysPermission> {

    String getParentId(Integer parentId);
}
