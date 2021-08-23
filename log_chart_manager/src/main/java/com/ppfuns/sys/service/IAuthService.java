package com.ppfuns.sys.service;

import com.ppfuns.sys.entity.LoginUser;
import com.ppfuns.sys.entity.SysPermission;
import com.ppfuns.sys.entity.SysRole;
import com.ppfuns.sys.entity.SysUser;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/3/10
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
public interface IAuthService {
    List<SysPermission> getUserPerms(Integer userId);

    LoginUser getLoginUser(String username);

    List<SysRole> getRoleByUser(Integer userId);

    List<SysPermission> findPermsByRoleId(Integer roleId);

    int updatePwd(Integer id, String s);

    void updateUser(SysUser updateUser);
}
