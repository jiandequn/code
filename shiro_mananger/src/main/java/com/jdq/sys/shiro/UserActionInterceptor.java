package com.jdq.sys.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdq.sys.entity.LoginUser;
import com.jdq.sys.service.IAuthService;
import com.jdq.util.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @项目名称：wyait-manage
 * @类名称：UserActionInterceptor
 * @类描述：判断用户信息是否已被后台更改，并根据更改的情况做对应的处理
 * @创建人：wyait
 * @创建时间：2018年5月2日 上午9:36:43 
 * @version：
 */
public class UserActionInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory
			.getLogger(UserActionInterceptor.class);
	@Autowired
	private IAuthService authService;
	
	private final static ObjectMapper objectMapper = new ObjectMapper();

	private final String kickoutUrl="/toLogin"; // 退出后重定向的地址

	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object obj, Exception e)
			throws Exception {
		// TODO Auto-generated method stub
		logger.debug("整个请求完成之后调用。DispatcherServlet视图渲染完成之后。（主要是用于进行资源清理工作）");

	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object obj, ModelAndView mv)
			throws Exception {
		// TODO Auto-generated method stub
		logger.debug("请求处理之后调用；在视图渲染之前，controller处理之后。");

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object obj) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("请求到达后台方法之前调用（controller之前）");
		// 1. SecurityUtils获取session中的用户信息
		// HttpSession session=request.getSession();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		if (user != null && StringUtils.isNotEmpty(user.getUsername())
				&& null != user.getVersion()) {
			// 2. 获取数据库中的用户数据
//			User dataUser = this.userService.findUserByMobile(user.getMobile());
			LoginUser dataUser = this.authService.getLoginUser(user.getUsername());
			// 3. 对比session中用户的version和数据库中的是否一致
			if (dataUser != null
					&& null != dataUser.getVersion()
					&& String.valueOf(user.getVersion()).equals(
							String.valueOf(dataUser.getVersion()))) {
				// 3.1 一样，放行
				return true;
			}else{
				// 3.2 不一样，这里统一做退出登录处理；//TODO 使用redis缓存用户权限数据，根据用户更新、用户权限更新；做对应的处理。
				SecurityUtils.getSubject().logout();
				isAjaxResponse(request,response);
			}
		}
		return false;
	}
	
	private boolean isAjaxResponse(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// ajax请求
		/**
		 * 判断是否已经踢出
		 * 1.如果是Ajax 访问，那么给予json返回值提示。
		 * 2.如果是普通请求，直接跳转到登录页
		 */
		//判断是不是Ajax请求
		ResultData responseResult = new ResultData();
		if (ShiroFilterUtils.isAjax(request) ) {
			logger.debug(getClass().getName()+ "，当前用户的信息或权限已变更，重新登录后生效！");
			responseResult.setCode("1102");
			responseResult.setMsg("您的信息或权限已变更，重新登录后生效");
			out(response, responseResult);
		}else{
			// 重定向
			WebUtils.issueRedirect(request, response, kickoutUrl);
		}
		return false;
	}
	
	/**
	 *
	 * @描述：response输出json
	 * @创建人：wyait
	 * @创建时间：2018年4月24日 下午5:14:22
	 * @param response
	 * @param result
	 */
	public static void out(HttpServletResponse response, ResultData result){
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");//设置编码
			response.setContentType("application/json");//设置返回类型
			out = response.getWriter();
			out.println(objectMapper.writeValueAsString(result));//输出
			logger.error("用户在线数量限制【wyait-manage-->UserActionInterceptor.out】响应json信息成功");
		} catch (Exception e) {
			logger.error("用户在线数量限制【wyait-manage-->UserActionInterceptor.out】响应json信息出错", e);
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
}
