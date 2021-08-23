package com.jdq.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdq.sys.entity.SysPermission;
import com.jdq.sys.mapper.PermissionMapper;
import com.jdq.sys.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, SysPermission> implements IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public String getParentId(Integer parentId) {
        if(parentId == 0){
            return "0";
        }
        SysPermission permission =permissionMapper.selectById(parentId);
        return permission.getParentIds().concat(",")+parentId;
    }
}
