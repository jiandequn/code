package com.example.controller;

import com.example.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @项目名称：lyd-channel
 * @包名：com.lyd.channel.web
 * @类描述：
 * @创建人：wyait
 * @创建时间：2017-11-28 18:52
 * @version：V1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {
	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);
	private static final String SESSION_KEY_OF_RAND_CODE = "randCode";
	@RequestMapping("/index")
	public String index() {
		logger.debug("-------------index------------");
		return "index";
	}

	@RequestMapping("/home")
	public String toHome(HttpServletRequest request) {
		logger.debug("===111-------------home------------");
		return "home";
	}

	
	@RequestMapping("/login")
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
		return "login";
	}
	@RequestMapping(value = "/{page}",method = RequestMethod.POST)
	public Result unAuth(HttpServletResponse response) throws IOException {
		logger.debug("-------------unAuth------------");
		return Result.getInstance();
	}
	@RequestMapping("/{page}")
	public ModelAndView toPage(@PathVariable("page") String page, HttpServletResponse response) {
		logger.debug("-------------toindex------------" + page);
		return new ModelAndView(page);
	}


}
