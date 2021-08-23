package com.jdq.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdq.sys.entity.LoginUser;
import com.jdq.sys.entity.SysPermission;
import com.jdq.sys.entity.SysRole;
import com.jdq.sys.entity.SysUser;
import com.jdq.sys.mapper.PermissionMapper;
import com.jdq.sys.mapper.RoleMapper;
import com.jdq.sys.mapper.UserMapper;
import com.jdq.sys.service.IAuthService;
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
