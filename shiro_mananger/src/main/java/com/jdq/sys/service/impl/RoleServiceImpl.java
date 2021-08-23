package com.jdq.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdq.sys.entity.SysRole;
import com.jdq.sys.entity.SysRolePermission;
import com.jdq.sys.mapper.RoleMapper;
import com.jdq.sys.mapper.RolePermissionMapper;
import com.jdq.sys.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    private static final Logger logger = LoggerFactory
            .getLogger(RoleServiceImpl.class);
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
    public int updateRole(SysRole role, String permIds) {
        int roleId=role.getId();
        String[] arrays=permIds.split(",");
        logger.debug("权限id =arrays="+arrays.toString());
        //1，更新角色表数据；
        int num=this.roleMapper.updateById(role);
        if(num<1){
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        //2，删除原角色权限；
        batchDelRolePerms(roleId);
        //3，添加新的角色权限数据；
        setRolePerms(roleId, arrays);
        return 1;
    }
    /**
     * 批量删除角色权限中间表数据
     * @param roleId
     */
    private void batchDelRolePerms(int roleId) {
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        this.rolePermissionMapper.delete(queryWrapper);
    }

    /**
     * 给当前角色设置权限
     * @param roleId
     * @param arrays
     */
    private void setRolePerms(int roleId, String[] arrays) {
        for (String permid : arrays) {
            SysRolePermission rpk=new SysRolePermission();
            rpk.setRoleId(roleId);
            rpk.setPermissionId(Integer.valueOf(permid));
            this.rolePermissionMapper.insert(rpk);
        }
    }
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
    public int delRole(int id) {
        //1.删除角色对应的权限
        batchDelRolePerms(id);
        //2.删除角色
        int num=this.roleMapper.deleteById(id);
        if(num<1){
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
    public int addRole(SysRole role, String permIds) {
        this.roleMapper.insert(role);
        int roleId=role.getId();
        String[] arrays=permIds.split(",");
        logger.debug("权限id =arrays="+arrays.toString());
        setRolePerms(roleId, arrays);
        return 1;
    }
}
