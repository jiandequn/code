package com.jdq.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdq.sys.entity.SysRolePermission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
public interface IRolePermissionService extends IService<SysRolePermission> {

    List getPermissionAll(Integer roleId);
}
