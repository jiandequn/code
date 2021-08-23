package com.jdq.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdq.sys.entity.SysRolePermission;
import com.jdq.util.LayerTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
public interface RolePermissionMapper extends BaseMapper<SysRolePermission> {

    List<LayerTree> getPermissionAll(@Param("parentId") Integer parentId, @Param("roleId") Integer roleId);
}
