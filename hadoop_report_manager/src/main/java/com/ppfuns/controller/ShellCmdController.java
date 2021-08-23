package com.ppfuns.controller;

import com.alibaba.druid.util.StringUtils;
import com.ppfuns.dao.RunClassDao;
import com.ppfuns.entity.ResultData;
import com.ppfuns.entity.base.TableInfo;
import com.ppfuns.entity.table.RunClassEntity;
import com.ppfuns.utils.ComboxEntity;
import com.ppfuns.utils.EntityProperties;
import com.ppfuns.utils.HadoopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/29
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/shell/cmd")
public class ShellCmdController {

    @Autowired
    private EntityProperties entityProperties;
    @Autowired
    private RunClassDao runClassDao;

    @RequestMapping("/combox")
    public List<ComboxEntity> select(TableInfo tableInfo) {
        List<ComboxEntity> list=  entityProperties.getHadoopRunClazz().stream().map(s->{
            String[] t = s.split(":");
            return new ComboxEntity(t[0],t[1]);
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * 用于执行hadoop命令
     * @param threadName
     * @return
     */
    @RequestMapping("/exec")
    public ResultData exec(String runClazzIds, String threadName,String timePattern) {
        if(StringUtils.isEmpty(threadName)){
            return new ResultData("0","参数不全");
        }
        List<RunClassEntity> runClazzes =runClassDao.getCanRun(runClazzIds);
        HadoopUtils.run(runClazzes,timePattern,threadName,entityProperties);
        return new ResultData();
    }

    @RequestMapping("status")
    public ResultData getThreadStatus(String threadName) {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数加倍
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // 获取根线程组的所有线程
        int actualSize = topGroup.enumerate(slackList);
        // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        System.out.println("Thread list size == " + list.length);
        String status = "0";
        for (Thread thread : list) {
            if(!StringUtils.isEmpty(thread.getName())){
                if(thread.getName().contains(threadName)){
                    status="1";
                }
            }
            System.out.println(thread.getName());
        }
        return new ResultData(status,"");
    }
}
