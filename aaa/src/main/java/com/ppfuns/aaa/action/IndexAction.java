package com.ppfuns.aaa.action;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 16-7-20
 * Time: 下午2:37
 * To change this template use File | Settings | File Templates.
 */
@Api(value = "/test") //swagger分类标题注解
@Controller
@RequestMapping(value="/api")
public class IndexAction {
    @ApiOperation(value = "index测试",httpMethod="GET")
    @RequestMapping(value="doc")
    public String doc(HttpServletRequest requset,HttpServletResponse response,@ApiParam(value = "内容ID值" ,required=true )String id){
        return "doc";
    }
}
