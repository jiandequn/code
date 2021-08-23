package com.jdq.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdq.sys.entity.SysPermission;
import com.jdq.sys.service.IPermissionService;
import com.jdq.sys.service.IRolePermissionService;
import com.jdq.util.LayerTree;
import com.jdq.util.ResultData;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/sys/permission")
public class PermissionController {
    private static final Logger logger = LoggerFactory
            .getLogger(PermissionController.class);
    @Autowired
    private IPermissionService iPermissionService;
    @Autowired
    private IRolePermissionService iRolePermissionService;
    /**
     * 权限列表
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView permList() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/sys/permission/permList");
        try {
            List<SysPermission> permList = iPermissionService.list();
            logger.debug("权限列表查询=permList:" + permList);
            mav.addObject("permList", permList);
            mav.addObject("msg", "ok");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("权限查询异常！", e);
        }
        return mav;
    }
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> list() {
        Map<String,Object> map = new HashMap<>();
        map.put("data",iPermissionService.list());
        map.put("code",0);
        map.put("count",555);
        return map;
    }
    /**
     * 添加权限
     * @param permission
     * @return ModelAndView ok/fail
     */
    @RequiresPermissions(value = { "sys_permission:add","sys_permission:update"},logical = Logical.OR)
    @RequestMapping(value = "/setPerm", method = RequestMethod.POST)
    @ResponseBody
    public ResultData setPerm(SysPermission permission) {
        logger.debug("设置权限--区分权限--permission-"
                + permission);
        try {
             if (null != permission) {
                Date date = new Date();
                if (permission.getId() != null) {
                    permission.setUpdateTime(date);
                    //编辑权限
                    String parentIds = this.iPermissionService.getParentId(permission.getParentId());
                    permission.setParentIds(parentIds);
                    this.iPermissionService.updateById(permission);
                } else if (permission.getId() == null) {
                    permission.setCreateTime(date);
                    //增加子节点权限
                    String parentIds = this.iPermissionService.getParentId(permission.getParentId());
                    permission.setParentIds(parentIds);
                    this.iPermissionService.save(permission);
                }
                logger.debug("设置权限成功！-permission-" + permission);
                return new ResultData();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置权限异常！", e);
        }
        return new ResultData("0","设置权限出错，请您稍后再试");
    }

    /**
     * 获取权限
     * @param id
     * @return
     */
    @RequestMapping(value = "/getPerm", method = RequestMethod.GET)
    @ResponseBody
    public SysPermission getPerm(
            @RequestParam("id") int id) {
        logger.debug("获取权限--id-" + id);
        try {
            if (id > 0) {
                SysPermission perm = this.iPermissionService.getById(id);
                logger.debug("获取权限成功！-permission-" + perm);
                return perm;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取权限异常！", e);
        }
        return null;
    }
    /**
     * 删除权限
     * @param id
     * @return
     */
    @RequiresPermissions(value = { "sys_permission:del"})
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultData del(
            @RequestParam("id") int id) {
        logger.debug("删除权限--id-" + id);
        try {
            if (id > 0) {
                QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("parent_id",id).or().apply("FIND_IN_SET('{0}',parent_ids)");
                List<SysPermission> childrenlist = this.iPermissionService.list(queryWrapper);
                if(CollectionUtils.isEmpty(childrenlist)){
                    if(this.iPermissionService.removeById(id)){
                        return new ResultData("删除成功");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除权限异常！", e);
        }
        return new ResultData("0","删除失败，请您稍后再试");
    }
    /**
     * 查询权限树数据
     * @return PermTreeDTO
     */
    @RequestMapping(value = "/treePerms", method = RequestMethod.GET)
    @ResponseBody
    public List<LayerTree> treePerms() {
        logger.debug("权限树列表！");
        List<LayerTree> pvo = null;
        try {
            pvo = iRolePermissionService.getPermissionAll(null);
            //生成页面需要的json格式
            logger.debug("权限树列表查询=pvo:" + pvo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("权限树列表查询异常！", e);
        }
        return pvo;
    }
}
