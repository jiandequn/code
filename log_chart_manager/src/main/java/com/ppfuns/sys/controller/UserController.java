package com.ppfuns.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.sys.entity.SysUser;
import com.ppfuns.sys.entity.SysUserRole;
import com.ppfuns.sys.service.IRoleService;
import com.ppfuns.sys.service.IUserRoleService;
import com.ppfuns.sys.service.IUserService;
import com.ppfuns.util.ResultData;
import com.ppfuns.util.ResultPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    private static final Logger logger = LoggerFactory
            .getLogger(UserController.class);
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserRoleService iUserRoleService;
    @Autowired
    private IRoleService iRoleService;
    /**
     * 跳转系统用户管理页面
     * @return
     */
     @RequestMapping("/toPage")
    public ModelAndView toPage(){
        return new ModelAndView("/sys/user/userList");
    }

    /**
     * 弹出框，弹出用户角色选择页面
     * @return
     */
    @RequestMapping("/userRoleSet")
    public ModelAndView userRoleSet(Integer userId){
        ModelAndView modelAndView = new ModelAndView("/sys/user/userRoleSet");
        modelAndView.addObject("roles",this.iRoleService.list());
        QueryWrapper<SysUserRole> uesrRoleWrapper = new QueryWrapper<>();
        uesrRoleWrapper.eq("user_id",userId);
        List<SysUserRole> list = this.iUserRoleService.list(uesrRoleWrapper);
        List<Integer> userRoles = list.stream().map(s->s.getRoleId()).collect(Collectors.toList());
        modelAndView.addObject("userRoles",userRoles);
        modelAndView.addObject("user",this.iUserService.getById(userId));
        return modelAndView;
    }

    @RequestMapping("/list")
    public ResultPage getList(String userName,String realName,String insertTimeStart,String insertTimeEnd,Integer page,Integer limit) {
        QueryWrapper<SysUser> qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(realName)) qw.eq("real_name",realName);
        if(StringUtils.isNotEmpty(userName)) qw.eq("user_name",userName);
        if(StringUtils.isNotEmpty(insertTimeStart)) qw.ge("create_time",insertTimeStart);
        if(StringUtils.isNotEmpty(insertTimeEnd)) qw.lt("create_time",insertTimeEnd);
        qw.orderByAsc("create_time");
        Page<SysUser> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iUserService.page(pg,qw));
    }
    /**
     * 设置用户是否离职
     * @return ok/fail
     */
    @RequiresPermissions(value = { "sys_user:setJob"})
    @RequestMapping(value = "/setIsJob", method = RequestMethod.POST)
    @ResponseBody
    public ResultData setJobUser(@RequestParam("id") Integer id,
                                 @RequestParam("job") boolean isJob,
                                 @RequestParam("version") Integer version) {
        logger.debug("设置用户是否离职！id:" + id + ",isJob:" + isJob + ",version:"
                + version);
        String msg = "";
        try {
            if (null == id || null == version) {
                logger.debug("设置用户是否离职，结果=请求参数有误，请您稍后再试");
                return new ResultData("0","请求参数有误，请您稍后再试");
            }
//            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
//            if (null == existUser) {
//                logger.debug("设置用户是否离职，结果=您未登录或登录超时，请您登录后再试");
//                return "您未登录或登录超时，请您登录后再试";
//            }
            // 设置用户是否离职
            SysUser user = new SysUser();
            user.setJob(isJob);
            user.setId(id);
            user.setVersion(version);
            iUserService.updateById(user);
//            msg = userService.setJobUser(id, isJob, existUser.getId(),version);
//            logger.info("设置用户是否离职成功！userID=" + id + ",isJob:" + isJob
//                    + "，操作的用户ID=" + existUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置用户是否离职异常！", e);
            return new ResultData("0","操作异常，请您稍后再试！");
        }
        return new ResultData();
    }
    /**
     * 获取用户信息
     * @return ok/fail
     */
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public ResultData getUserAndRoles(@RequestParam("id") Integer id) {
        //得到用户信息
        SysUser sysUser = iUserService.getById(id);
        return new ResultData("1","获取成功",sysUser);
    }
    /**
     * 设置用户[新增或更新]
     * @return ok/fail
     */
    @RequiresPermissions(value = { "sys_user:add","sys_user:update"},logical = Logical.OR)
    @RequestMapping(value = "/setUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultData setUser(SysUser user) {
        logger.debug("设置用户[新增或更新]！user:" + user);
        try {
            if (null == user) {
                logger.debug("置用户[新增或更新]，结果=请您填写用户信息");
                return new ResultData("0","请您填写用户信息");
            }
//            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
//            if (null == existUser) {
//                logger.debug("置用户[新增或更新]，结果=您未登录或登录超时，请您登录后再试");
//                return "您未登录或登录超时，请您登录后再试";
//            }
            if(user.getId() == null){
                user.setCreateTime(new Date());
                user.setJob(true);
                user.setCreateUser("t");
            }
            user.setUpdateTime(new Date());
            user.setUpdateUser("tt");
            // 设置用户[新增或更新]
            String mg = iUserService.setUser(user);
            if(!StringUtils.isEmpty(mg)) return new ResultData("0",mg);
            return new ResultData();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置用户[新增或更新]异常！", e);
            return new ResultData("0","操作异常，请您稍后再试");
        }
    }
    /**
     * 删除用户
     * @return ok/fail
     */
    @RequiresPermissions(value = { "sys_user:del"})
    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultData delUser(@RequestParam("id") Integer id,
                          @RequestParam("version") Integer version) {
        logger.debug("删除用户！id:" + id);
        String msg = "";
        try {
            if (null == id || null == version) {
                logger.debug("删除用户，结果=请求参数有误，请您稍后再试");
                return new ResultData("0","请求参数有误，请您稍后再试");
            }
//            LoginUser existUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//            if (null == existUser) {
//                logger.debug("删除用户，结果=您未登录或登录超时，请您登录后再试");
//                return "您未登录或登录超时，请您登录后再试";
//            }
            // 删除用户
            SysUser dataUser = this.iUserService.getById(id);
            // 版本不一致
            if (null != dataUser
                    && null != dataUser.getVersion()
                    && !String.valueOf(version).equals(
                    String.valueOf(dataUser.getVersion()))) {
                return new ResultData("0","操作失败，请您稍后再试");
            }
            dataUser.setDel(true);
            dataUser.setUpdateTime(new Date());
            iUserService.updateById(dataUser);
            QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",id);
            iUserRoleService.remove(queryWrapper);
            logger.info("删除用户:" + msg + "。userId=" + id + "，操作用户id:"
                    + dataUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除用户异常！", e);
            return new ResultData("0","操作异常，请您稍后再试");
        }
        return new ResultData();
    }
    /**
     * 恢复删除用户
     * @return ok/fail
     */
    @RequiresPermissions(value = { "sys_user:recover"},logical= Logical.OR)
    @RequestMapping(value = "/recoverUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultData recoverUser(@RequestParam("id") Integer id,
                              @RequestParam("version") Integer version) {
        logger.debug("恢复删除用户！id:" + id);
        String msg = "";
        try {
            if (null == id || null == version) {
                logger.debug("恢复删除用户，结果=请求参数有误，请您稍后再试");
                return new ResultData("0","请求参数有误，请您稍后再试");
            }
            SysUser dataUser = this.iUserService.getById(id);

            dataUser.setDel(false);
            dataUser.setUpdateTime(new Date());
            iUserService.updateById(dataUser);
            logger.info("恢复删除用户:" + msg + "。userId=" + id + "，操作用户id:"
                    + dataUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("恢复删除用户异常！", e);
            return new ResultData("0","操作异常，请您稍后再试");
        }
        return new ResultData();
    }
    @RequiresPermissions(value = { "sys_user:resetPwd"})
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultData resetPwd(@RequestParam("id") Integer id,
                                  @RequestParam("password") String password) {
        logger.debug("重置密码！id:" + id);
        String msg = "";
        try {
            if (null == id || StringUtils.isEmpty(password)) {
                logger.debug("重置用户密码，结果=请求参数有误，请您稍后再试");
                return new ResultData("0","请求参数有误，请您稍后再试");
            }
            SysUser user = new SysUser();
            user.setId(id);
            user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            user.setUpdateTime(new Date());
            iUserService.updateById(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("重置用户密码异常！", e);
            return new ResultData("0","操作异常，请您稍后再试");
        }
        return new ResultData();
    }

}
