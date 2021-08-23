package com.ppfuns.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.sys.entity.SysRole;
import com.ppfuns.sys.service.IPermissionService;
import com.ppfuns.sys.service.IRolePermissionService;
import com.ppfuns.sys.service.IRoleService;
import com.ppfuns.util.ResultData;
import com.ppfuns.util.ResultPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {
    private static final Logger logger = LoggerFactory
            .getLogger(RoleController.class);
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IRolePermissionService iRolePermissionService;
    @Autowired
    private IPermissionService iPermissionService;

    /**
     * 跳转到角色列表
     *
     * @return
     */
    @RequestMapping("/toPage")
    public ModelAndView toPage() {
        return new ModelAndView("/sys/role/roleManage");
    }

    /**
     * 根据id查询角色
     *
     * @return PermTreeDTO
     */
    @RequestMapping(value = "/updateRole/{id}")
    //@ResponseBody
    public ModelAndView updateRole(@PathVariable("id") Integer id) {
        logger.debug("根据id查询角色id：" + id);
        ModelAndView mv = new ModelAndView("/sys/role/roleManage");
        try {
            if (null == id) {
                mv.addObject("msg", "请求参数有误，请您稍后再试");
                return mv;
            }
            mv.addObject("flag", "updateRole");
            SysRole rvo = iRoleService.getById(id);
            //获取全部权限数据
            //获取所用权限；并标记举用户权限
            List listPerms = this.iRolePermissionService.getPermissionAll(id);
            mv.addObject("permList", listPerms.toArray());
            //角色详情
            mv.addObject("roleDetail", rvo);
            logger.debug("根据id查询角色数据：" + mv);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加角色并授权！异常！", e);
            mv.addObject("msg", "请求异常，请您稍后再试");
        }
        return mv;
    }

    @RequestMapping(value = "/get/{id}")
    public Map<String, Object> get(@PathVariable("id") Integer id) {
        Map<String, Object> mp = new HashMap();
        SysRole rvo = iRoleService.getById(id);
        //获取全部权限数据
        //获取所用权限；并标记举用户权限
        List listPerms = this.iRolePermissionService.getPermissionAll(id);
        mp.put("permList", listPerms);
        //角色详情
        mp.put("roleDetail", rvo);
        return mp;
    }

    /**
     * 获取用户权限下所有角色信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/getAll")
    public List<SysRole> getAll(HttpServletRequest request) {
        return iRoleService.list();
    }

    /**
     * 角色列表
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/getRoleList", method = RequestMethod.GET)
    @ResponseBody
    public List<SysRole> getRoleList() {
        logger.debug("角色列表！");
        List<SysRole> roleList = null;
        try {
            roleList = iRoleService.list();
            logger.debug("角色列表查询=roleList:" + roleList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("角色查询异常！", e);
        }
        return roleList;
    }

    @RequestMapping("/list")
    public ResultPage getList(Integer page, Integer limit) {
        QueryWrapper<SysRole> qw = new QueryWrapper();
        qw.orderByAsc("create_time");
        Page<SysRole> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(iRoleService.page(pg, qw));
    }

    /**
     * 更新角色并授权
     *
     * @return PermTreeDTO
     */
    @RequiresPermissions(value = {"sys_permission:update"})
    @RequestMapping(value = "/setRole", method = RequestMethod.POST)
    @ResponseBody
    public ResultData setRole(@RequestParam("rolePermIds") String permIds, SysRole role) {
        logger.debug("更新角色并授权！角色数据role：" + role + "，权限数据permIds：" + permIds);
        try {
            if (StringUtils.isEmpty(permIds)) {
                return new ResultData("0", "未授权，请您给该角色授权");
            }
            if (null == role) {
                return new ResultData("0", "请您填写完整的角色数据");
            }
            role.setUpdateTime(new Date());
            if (iRoleService.updateRole(role, permIds) == 1) {
                return new ResultData("更新角色成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新角色并授权！异常！", e);
        }
        return new ResultData("0", "操作错误，请您稍后再试");
    }

    /**
     * 删除角色以及它对应的权限
     *
     * @param id
     * @return
     */
    @RequiresPermissions(value = {"sys_permission:del"})
    @RequestMapping(value = "/delRole", method = RequestMethod.POST)
    @ResponseBody
    public ResultData delRole(
            @RequestParam("id") int id) {
        logger.debug("删除角色以及它对应的权限--id-" + id);
        try {
            if (id > 0) {
                if (this.iRoleService.delRole(id) == 1) {
                    return new ResultData("删除成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除角色异常！", e);
        }
        return new ResultData("0", "删除失败");
    }

    /**
     * 添加角色并授权
     *
     * @return PermTreeDTO
     */
    @RequiresPermissions(value = {"sys_role:add"})
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ResponseBody
    public ResultData addRole(@RequestParam("permIds") String permIds, SysRole role) {
        logger.debug("添加角色并授权！角色数据role：" + role + "，权限数据permIds：" + permIds);
        try {
            if (StringUtils.isEmpty(permIds)) {
                return new ResultData("0", "未授权，请您给该角色授权");
            }
            if (null == role) {
                return new ResultData("0", "请您填写完整的角色数据");
            }
            role.setCreateTime(new Date());
            if (iRoleService.addRole(role, permIds) == 1) {
                return new ResultData("角色添加成功");
            }
            return new ResultData("0", "角色添加失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加角色并授权！异常！", e);
        }
        return new ResultData("0", "操作错误，请您稍后再试");
    }
}
