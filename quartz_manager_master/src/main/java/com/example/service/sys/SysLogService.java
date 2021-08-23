package com.example.service.sys;

import com.example.dao.sys.SysLogMapper;
import com.example.dto.PageDTO;
import com.example.model.sys.SysLog;
import com.example.model.sys.User;
import com.example.utils.*;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/6/4
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    public void addLog(LogOpTypeEnum opTypeEnum, LogLevelEnum levelEnum, String content){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysLog log = new SysLog();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        log.setUserId(user.getId()+"");
        log.setUserName(user.getUsername());
        log.setRealName(user.getRealName());
        log.setBroswer(BrowserUtils.checkBrowse(request));
        log.setIp(IpUtil.getIpAddr(request));
        log.setOpTime(new Date());
        log.setOpType(opTypeEnum.getId());
        log.setLevel(levelEnum.getId());
        log.setContent(content);
        String requestURI=request.getRequestURI();
        log.setReqUri(requestURI);
        this.sysLogMapper.insertSelective(log);
    }

    public PageDataResult getList(PageDTO pageDTO) {
        //时间处理
        PageDataResult pdr = new PageDataResult();
        List<SysLog> list = sysLogMapper.getList(pageDTO);
        // 获取分页查询后的数据
        PageInfo<SysLog> pageInfo = new PageInfo<>(list);
        // 设置获取到的总记录数total：
        Integer count = sysLogMapper.getCount(pageDTO);
        pdr.setTotals(count);
        pdr.setList(list);
        return pdr;
    }
}
