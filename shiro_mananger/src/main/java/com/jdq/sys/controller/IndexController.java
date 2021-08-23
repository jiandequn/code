package com.jdq.sys.controller;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.jdq.sys.entity.LoginUser;
import com.jdq.sys.entity.SysPermission;
import com.jdq.sys.entity.SysUser;
import com.jdq.sys.service.IAuthService;
import com.jdq.util.ResultData;
import com.jdq.util.ValidateUtil;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/3/9
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    private static final Logger logger = LoggerFactory
            .getLogger(IndexController.class);
    @Autowired
    private IAuthService iAuthService;
    @Autowired
    private EhCacheManager ecm;
    @Autowired
    private ShiroDialect shiroDialect;
    @RequestMapping("/home")
    public String toHome(){
        return "/sys/home";
    }

    /**
     * 根据用户id查询权限树数据
     * @return PermTreeDTO
     */
    @RequestMapping(value = "/auth/getUserPerms", method = RequestMethod.GET)
    @ResponseBody
    public List<SysPermission> getUserPerms() {
        logger.debug("根据用户id查询限树列表！");
        List<SysPermission> pvo = null;
        LoginUser existUser= (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(null==existUser){
            logger.debug("根据用户id查询限树列表！用户未登录");
            return pvo;
        }
        try {
            pvo = iAuthService.getUserPerms(existUser.getId());
            //生成页面需要的json格式
            logger.debug("根据用户id查询限树列表查询=pvo:" + pvo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据用户id查询权限树列表查询异常！", e);
        }
        return pvo;
    }

    /**
     * 跳转登录页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();//根据请求数据，找到cookie数组
        boolean rememberMe = false;
        if (null != cookies) {//如果没有cookie数组
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("rememberMe")){
                    rememberMe = true;
                    break;
                }
            }
        }
        request.setAttribute("rememberMe",rememberMe);
        logger.debug("===111-------------login------------");
        return "/sys/login";
    }
    /**
     * @描述：校验请求参数
     * @param obj
     * @param response
     * @return
     */
    protected boolean validatorRequestParam(Object obj, ResultData response) {
        boolean flag = false;
        Validator validator = new Validator();
        List<ConstraintViolation> ret = validator.validate(obj);
        if (ret.size() > 0) {
            // 校验参数有误
            response.setCode("0");
            response.setMsg(ret.get(0).getMessageTemplate());
        } else {
            flag = true;
        }
        return flag;
    }
    /**
     * 登录【使用shiro中自带的HashedCredentialsMatcher结合ehcache（记录输错次数）配置进行密码输错次数限制】
     * </br>缺陷是，无法友好的在后台提供解锁用户的功能，当然，可以直接提供一种解锁操作，清除ehcache缓存即可，不记录在用户表中；
     * </br>
     * @param user
     * @param rememberMe
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResultData login(HttpServletRequest request,
                            LoginUser user,
                            @RequestParam(value = "rememberMe", required = false) boolean rememberMe) {
        logger.debug("用户登录，请求参数=user:" + user + "，是否记住我：" + rememberMe);
        ResultData responseResult = new ResultData();
        responseResult.setCode("0");
        if (null == user) {
            responseResult.setMsg("请求参数有误，请您稍后再试");
            logger.debug("用户登录，结果=responseResult:" + responseResult);
            return responseResult;
        }
        if (!validatorRequestParam(user, responseResult)) {
            logger.debug("用户登录，结果=responseResult:" + responseResult);
            return responseResult;
        }

        // 用户是否存在
        LoginUser existUser = this.iAuthService.getLoginUser(user.getUsername());
        if (existUser == null) {
            responseResult.setMsg("该用户不存在，请您联系管理员");
            logger.debug("用户登录，结果=responseResult:" + responseResult);
            return responseResult;
        } else {
            // 是否离职
            if (existUser.getJob()) {
                responseResult.setMsg("登录用户已离职，请您联系管理员");
                logger.debug("用户登录，结果=responseResult:" + responseResult);
                return responseResult;
            }
        }
        // 用户登录
        try {
            // 1、 封装用户名、密码、是否记住我到token令牌对象 [支持记住我]
            AuthenticationToken token = new UsernamePasswordToken(
                    existUser.getUsername(), DigestUtils.md5DigestAsHex(user.getPassword().getBytes()),
                    rememberMe);
            // 2、 Subject调用login
            Subject subject = SecurityUtils.getSubject();
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.debug("用户登录，用户验证开始！user=" + existUser.getUsername());
            subject.login(token);
            responseResult.setCode("1");
            logger.info("用户登录，用户验证通过！user=" + existUser.getUsername());
//            sysLogService.addLog(LogOpTypeEnum.LOGIN, LogLevelEnum.INFO,"用户登录");
        } catch (UnknownAccountException uae) {
            logger.error("用户登录，用户验证未通过：未知用户！user=" + user.getUsername(), uae);
            responseResult.setMsg("该用户不存在，请您联系管理员");
        } catch (IncorrectCredentialsException ice) {
            // 获取输错次数
            logger.error("用户登录，用户验证未通过：错误的凭证，密码输入错误！user=" + user.getUsername(),
                    ice);
            responseResult.setMsg("用户名或密码不正确");
        } catch (LockedAccountException lae) {
            logger.error("用户登录，用户验证未通过：账户已锁定！user=" +  user.getUsername(), lae);
            responseResult.setMsg("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.error(
                    "用户登录，用户验证未通过：错误次数大于5次,账户已锁定！user=.getUsername()" + user, eae);
            responseResult
                    .setMsg("用户名或密码错误次数大于5次,账户已锁定!</br><span style='color:red;font-weight:bold; '>2分钟后可再次登录，或联系管理员解锁</span>");
            // 这里结合了，另一种密码输错限制的实现，基于redis或mysql的实现；也可以直接使用RetryLimitHashedCredentialsMatcher限制5次
        } /*catch (DisabledAccountException sae){
		 logger.error("用户登录，用户验证未通过：帐号已经禁止登录！user=" +
		 user.getMobile(),sae);
		 responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
		 responseResult.setMessage("帐号已经禁止登录");
		}*/catch (AuthenticationException ae) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.error("用户登录，用户验证未通过：认证异常，异常信息如下！user=" + user.getUsername(),
                    ae);
            responseResult.setMsg("用户名或密码不正确");
        } catch (Exception e) {
            logger.error("用户登录，用户验证未通过：操作异常，异常信息如下！user=" + user.getUsername(), e);
            responseResult.setMsg("用户登录失败，请您稍后再试");
        }
        Cache<String, AtomicInteger> passwordRetryCache = ecm
                .getCache("passwordRetryCache");
        if (null != passwordRetryCache) {
            int retryNum = (passwordRetryCache.get(existUser.getUsername()) == null ? 0
                    : passwordRetryCache.get(existUser.getUsername())).intValue();
            logger.debug("输错次数：" + retryNum);
            if (retryNum > 0 && retryNum < 6) {
                responseResult.setMsg("用户名或密码错误" + retryNum + "次,再输错"
                        + (6 - retryNum) + "次账号将锁定");
            }
        }
        logger.debug("用户登录，user=" + user.getUsername() + ",登录结果=responseResult:"
                + responseResult);
        return responseResult;
    }
    /**
     * 跳转登录页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String toHome(HttpServletRequest request) {
        return "/sys/home";
    }
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        System.out.println(shiroDialect.getPrefix());
        return "/sys/index";
    }
    /**
     * 得到当前登录用户信息详情
     * @return
     */
    @RequestMapping(value = "getCurrentUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultData getCurrentUser() {
        ResultData responseResult = new ResultData();
        try {
            // 判断用户是否登录
            LoginUser existUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                responseResult.setMsg("您未登录或登录超时，请您重新登录后再试");
                logger.debug("修改登录用户信息，结果=responseResult:" + responseResult);
                return responseResult;
            } else {
                LoginUser u = iAuthService.getLoginUser(existUser.getUsername());
                responseResult.setResult(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseResult.setCode("0");
            responseResult.setMsg("操作失败，请您稍后再试");
            logger.error("修改异常！", e);
            return responseResult;
        }
        logger.debug("修改登录用户信息成功，结果=responseResult:" + responseResult);
        return responseResult;
    }
    @RequestMapping(value = "updateUserByMe", method = RequestMethod.POST)
    @ResponseBody
    public ResultData updateUserByMe(@RequestParam("mobile") String mobile,
                                         @RequestParam("email") String email,
                                         @RequestParam("realName") String realName) {
        ResultData responseResult = new ResultData();
        responseResult.setCode("0");
        try {
            if (!ValidateUtil.isMobilephone(mobile)) {
                responseResult.setMsg("手机号格式有误，请您重新填写");
                logger.debug("修改手机号，结果=responseResult:" + responseResult);
                return responseResult;
            }
            if (!ValidateUtil.isEmail(email)) {
                responseResult.setMsg("邮箱格式有误，请您重新填写");
                logger.debug("邮箱格式有误，结果=responseResult:" + responseResult);
                return responseResult;
            }
            // 判断用户是否登录
            LoginUser existUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                responseResult.setMsg("您未登录或登录超时，请您重新登录后再试");
                logger.debug("修改登录用户信息，结果=responseResult:" + responseResult);
                return responseResult;
            } else {
                SysUser updateUser = new SysUser();
                updateUser.setEmail(email);
                updateUser.setRealName(realName);
                updateUser.setTelephone(mobile);
                updateUser.setId(existUser.getId());
                updateUser.setUpdateTime(new Date());
                this.iAuthService.updateUser(updateUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseResult.setCode("0");
            responseResult.setMsg("操作失败，请您稍后再试");
            logger.error("修改异常！", e);
            return responseResult;
        }
        responseResult.setCode("1");
        responseResult.setMsg("SUCCESS");
        logger.debug("修改登录用户信息成功，结果=responseResult:" + responseResult);
        return responseResult;
    }
    /**
     * 修改密码
     * @param pwd
     * @param isPwd
     * @return
     */
    @RequestMapping(value = "setPwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultData setPwd(@RequestParam("pwd") String pwd,
                                 @RequestParam("isPwd") String isPwd) {
        logger.debug("修改密码！pwd:" + pwd + ",isPwd=" + isPwd);
        ResultData responseResult = new ResultData();
        try {
            if (!ValidateUtil.isSimplePassword(pwd)
                    || !ValidateUtil.isSimplePassword(isPwd)) {
                responseResult.setCode("0");
                responseResult.setMsg("密码格式有误，请您重新填写");
                logger.debug("修改密码，结果=responseResult:" + responseResult);
                return responseResult;
            }
            if (!pwd.equals(isPwd)) {
                responseResult.setCode("0");
                responseResult.setMsg("两次密码输入不一致，请您重新填写");
                logger.debug("发修改密码，结果=responseResult:" + responseResult);
                return responseResult;
            }
            // 判断用户是否登录
            LoginUser existUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                responseResult.setCode("0");
                responseResult.setMsg("您未登录或登录超时，请您重新登录后再试");
                logger.debug("修改密码，结果=responseResult:" + responseResult);
                return responseResult;
            }
            // 修改密码
            int num = this.iAuthService.updatePwd(existUser.getId(),DigestUtils.md5DigestAsHex(pwd.getBytes()));
            if (num != 1) {
                responseResult.setCode("0");
                responseResult.setMsg("操作失败，请您稍后再试");
                logger.debug("修改密码失败，已经离职或该用户被删除！结果=responseResult:"
                        + responseResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseResult.setCode("0");
            responseResult.setMsg("操作失败，请您稍后再试");
            logger.error("修改密码异常！", e);
        }
        logger.debug("修改密码，结果=responseResult:" + responseResult);
        return responseResult;
    }
    /**
     * 验证输入密码是否符合登录用户本人
     * @param pwd
     * @return
     */
    @RequestMapping(value = "validatePwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultData validatePwd(@RequestParam("oldPwd") String pwd) {
        logger.debug("修改密码！pwd:" + pwd + ",isPwd=" + pwd);
        ResultData responseResult = new ResultData();
        try {
//			if (!ValidateUtil.isSimplePassword(pwd)
//					|| !ValidateUtil.isSimplePassword(pwd)) {
//				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
//						.getCode());
//				responseResult.setMessage("密码格式有误，请您重新填写");
//				logger.debug("修改密码，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
            // 判断用户是否登录
            LoginUser existUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                responseResult.setCode("0");
                responseResult.setMsg("您未登录或登录超时，请您重新登录后再试");
                logger.debug("修改密码，结果=responseResult:" + responseResult);
                return responseResult;
            }
            // 修改密码
            LoginUser user = this.iAuthService.getLoginUser(existUser.getUsername());
            if(!DigestUtils.md5DigestAsHex(pwd.getBytes()).equals(user.getPassword())){
                responseResult.setCode("0");
                responseResult.setMsg("操作失败，请您稍后再试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseResult.setCode("0");
            responseResult.setMsg("操作失败，请您稍后再试");
            logger.error("修改密码异常！", e);
        }
        logger.debug("修改密码，结果=responseResult:" + responseResult);
        return responseResult;
    }

    public ModelAndView logout(HttpServletRequest request){
        return new ModelAndView("/login");
    }
}
