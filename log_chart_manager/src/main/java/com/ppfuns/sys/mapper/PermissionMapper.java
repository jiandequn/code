package com.ppfuns.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ppfuns.sys.entity.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
public interface PermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> getUserPerms(@Param("userId")Integer userId);
    @Select("SELECT p.* FROM sys_role_permission rp,sys_permission p WHERE rp.permission_id=p.id and rp.role_id=#{roleId}")
    List<SysPermission> findPermsByRoleId(@Param("roleId") Integer roleId);
}
