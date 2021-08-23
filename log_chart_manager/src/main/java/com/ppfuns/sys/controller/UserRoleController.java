package com.ppfuns.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ppfuns.sys.entity.SysRole;
import com.ppfuns.sys.entity.SysUserRole;
import com.ppfuns.sys.service.IRoleService;
import com.ppfuns.sys.service.IUserRoleService;
import com.ppfuns.util.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户角色关联表 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/sys/user-role")
public class UserRoleController {

    private static final Logger logger = LoggerFactory
            .getLogger(UserRoleController.class);
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;
    @RequestMapping("/get")
    public List<SysRole> getList(SysUserRole userRole){
        QueryWrapper wrapper = new QueryWrapper<SysUserRole>(userRole);
        List<SysUserRole> list =  userRoleService.list(wrapper);
        List<SysRole> listr = new ArrayList(list.size());
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(s->{
                SysRole role = this.roleService.getById(s.getRoleId());
                listr.add(role);
            });
        }
        return listr;
    }
    /**
     * 添加用户角色
     * @return ok/fail
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultData setUser(@RequestParam("roleIds") String roleIds, Integer userId) {
        logger.debug("设置用户[新增或更新]！userId:" + userId + ",roleIds:" + roleIds);
        try {
            if(userId != null){
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userId);
                QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>(userRole);
//                queryWrapper.eq("user_id",userId);
                this.userRoleService.remove(queryWrapper);
                if(!StringUtils.isEmpty(roleIds)){
                    Arrays.asList(roleIds.split(",")).forEach(s->{
                        userRole.setRoleId(Integer.valueOf(s));
                        this.userRoleService.save(userRole);
                    });
                }
            }

            return new ResultData();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置用户[新增或更新]异常！", e);
            return new ResultData("0","操作异常，请您稍后再试");
        }
    }
}
