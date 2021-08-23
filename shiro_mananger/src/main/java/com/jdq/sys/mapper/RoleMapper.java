package com.jdq.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdq.sys.entity.SysRole;
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
public interface RoleMapper extends BaseMapper<SysRole> {

    @Select("SELECT r.* FROM sys_user_role ur,sys_role r WHERE ur.role_id=r.id and ur.user_id=#{userId}")
    List<SysRole> getRoleByUser(@Param("userId") Integer userId);
}
