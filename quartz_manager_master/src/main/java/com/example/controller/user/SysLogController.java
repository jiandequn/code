package com.example.controller.user;

import com.example.dto.PageDTO;
import com.example.dto.SysLogSearchDTO;
import com.example.service.sys.SysLogService;
import com.example.utils.PageDataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/6/5
 * Time: 10:11
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "sys/log")
public class SysLogController {
    private static final Logger logger = LoggerFactory
            .getLogger(SysLogController.class);
    @Autowired
    private SysLogService sysLogService;
     @RequestMapping("/toPage")
    public String toPage(HttpServletRequest request){
        return "auth/sysLogList";
    }


    /**
     * 分页查询用户列表
     * @return ok/fail
     */
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
//    @RequiresPermissions(value="sys.log:query")
    public PageDataResult getList(@RequestParam(name = "page",defaultValue="1") Integer page,
                                   @RequestParam(name= "limit",defaultValue="10") Integer limit, SysLogSearchDTO searchDTO) {
        logger.debug("分页查询用户列表！搜索条件：SysLogSearchDTO：" + searchDTO + ",page:" + page
                + ",每页记录数量limit:" + limit);
        PageDataResult pdr = new PageDataResult();
        try {
            PageDTO pageDTO = new PageDTO<SysLogSearchDTO>(searchDTO,page,limit);
            pdr = sysLogService.getList(pageDTO);
            logger.debug("用户列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户列表查询异常！", e);
        }
        return pdr;
    }
}
