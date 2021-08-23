package com.jdq.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdq.sys.entity.SysRolePermission;
import com.jdq.sys.mapper.PermissionMapper;
import com.jdq.sys.mapper.RolePermissionMapper;
import com.jdq.sys.service.IRolePermissionService;
import com.jdq.util.LayerTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, SysRolePermission> implements IRolePermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public List getPermissionAll(Integer roleId) {
        return getNode(0,roleId);
    }
    private List<LayerTree> getNode(Integer parentId, Integer roleId){
        List<LayerTree> list  = this.rolePermissionMapper.getPermissionAll(parentId,roleId);
        if(!CollectionUtils.isEmpty(list)){
            for(LayerTree node : list){
                List<LayerTree> children =  getNode(Integer.valueOf(node.getId()),roleId);
                if(!CollectionUtils.isEmpty(children)){
                    node.setChildren(children);
                }
            }
            return list;
        }
        return null;
    }
}
