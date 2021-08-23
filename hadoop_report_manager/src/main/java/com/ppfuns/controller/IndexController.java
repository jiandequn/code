package com.ppfuns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/5/16
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping("/{pathName}")
    public String index(@PathVariable String pathName){
        return pathName;
    }
    @RequestMapping("view/{pathName}")
    public String viewPath(@PathVariable String pathName){
        return "view/".concat(pathName);
    }
    @RequestMapping("view/{pathName}/{page}")
    public String viewOther(@PathVariable String pathName,@PathVariable String page){
            return "view/".concat(pathName).concat("/").concat(page);
    }
    @RequestMapping("index/tt")
    @ResponseBody
    public String tt(){
        Map<String,String> m= System.getenv();
        m.forEach((k,v)-> System.out.println(k+":"+v));
        Properties p = System.getProperties();
        p.keySet().forEach(a-> System.out.println(a));
        return null;
    }
}
