package com.jdq.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdq.sys.entity.SysUser;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
public interface IUserService extends IService<SysUser> {

    String setUser(SysUser user);
}
