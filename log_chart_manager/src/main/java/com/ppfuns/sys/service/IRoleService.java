package com.ppfuns.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ppfuns.sys.entity.SysRole;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
public interface IRoleService extends IService<SysRole> {

    int updateRole(SysRole role, String permIds);

    int delRole(int id);

    int addRole(SysRole role, String permIds);
}
