package com.ppfuns.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ppfuns.sys.entity.LoginUser;
import com.ppfuns.sys.entity.SysPermission;
import com.ppfuns.sys.entity.SysRole;
import com.ppfuns.sys.entity.SysUser;
import com.ppfuns.sys.mapper.PermissionMapper;
import com.ppfuns.sys.mapper.RoleMapper;
import com.ppfuns.sys.mapper.UserMapper;
import com.ppfuns.sys.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/3/10
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<SysPermission> getUserPerms(Integer userId) {
        return permissionMapper.getUserPerms(userId);
    }

    @Override
    public LoginUser getLoginUser(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        queryWrapper.eq("is_del",0);
        SysUser sysUser = this.userMapper.selectOne(queryWrapper);
        if(sysUser == null) return  null;
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(sysUser.getUserName());
        loginUser.setId(sysUser.getId());
        loginUser.setPassword(sysUser.getPassword());
        loginUser.setRealName(sysUser.getRealName());
        loginUser.setTelephone(sysUser.getTelephone());
        loginUser.setJob(sysUser.getJob());
        loginUser.setEmail(sysUser.getEmail());
        return loginUser;
    }

    @Override
    public List<SysRole> getRoleByUser(Integer userId) {
        return this.roleMapper.getRoleByUser(userId);
    }

    @Override
    public List<SysPermission> findPermsByRoleId(Integer roleId) {
        return permissionMapper.findPermsByRoleId(roleId);
    }

    /**
     * 修改已加密密码
     * @param id
     * @param pwd
     * @return
     */
    @Override
    public int updatePwd(Integer id, String pwd) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setPassword(pwd);
        return this.userMapper.updateById(user);
    }

    @Override
    public void updateUser(SysUser updateUser) {
          this.userMapper.updateById(updateUser);
    }
}
