package com.jdq.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdq.sys.entity.SysUser;
import com.jdq.sys.mapper.UserMapper;
import com.jdq.sys.mapper.UserRoleMapper;
import com.jdq.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {
            RuntimeException.class, Exception.class })
    @Override
    public String setUser(SysUser user) {
        SysUser queryUser = new SysUser();
        queryUser.setTelephone(user.getTelephone());
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>(queryUser);
        List<SysUser> existUser = this.userMapper.selectList(wrapper);  //查询手机是否重复
        if (!CollectionUtils.isEmpty(existUser)) {
            if(user.getId() == null ||(user.getId()!=null && !existUser.stream().allMatch(s->s.getId().equals(user.getId()))))
                return "该手机号已经存在";
        }
        queryUser.setTelephone(null); queryUser.setUserName(user.getUserName());
        existUser = this.userMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(existUser)) {
            if(user.getId() == null ||(user.getId()!=null && !existUser.stream().allMatch(s->s.getId().equals(user.getId()))))
                return "该用户名已经存在";
        }
        user.setUpdateTime(new Date());
        if(user.getId() == null){
            user.setDel(false);
            user.setJob(false);
            user.setCreateTime(new Date());
            // 设置加密密码
            if (StringUtils.isNotBlank(user.getPassword())) {
                user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            } else {
                user.setPassword(DigestUtils.md5DigestAsHex("654321".getBytes()));
            }
            this.userMapper.insert(user);
        }else {
            // 判断用户是否已经存在
            SysUser dataUser = this.userMapper.selectById(user.getId());
            // 版本不一致
            if (null != dataUser
                    && null != dataUser.getVersion()
                    && !String.valueOf(user.getVersion()).equals(
                    String.valueOf(dataUser.getVersion()))) {
                return "操作失败，请您稍后再试";
            }
            // 设置加密密码
            if (StringUtils.isNotBlank(user.getPassword())) {
                user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).toString());
            }
            this.userMapper.updateById(user);
        }
        return "";
    }
}
