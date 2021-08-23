package com.example.controller;

import com.example.dto.ScheduleJobDTO;
import com.example.model.ScheduleJob;
import com.example.model.base.ResultData;
import com.example.schedule.InitScheduleTask;
import com.example.service.ScheduleJobService;
import com.example.utils.PageDataResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by FlySheep on 17/3/25.
 */
@Controller
@RequestMapping("/quartz")
public class ScheduleJobController {
    private static final Logger logger = LoggerFactory
            .getLogger(ScheduleJobController.class);
    @Autowired
    private ScheduleJobService scheduleJobService;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @RequestMapping("/list")
    public String toPage(HttpServletRequest request) {
        return "/quartz/scheduleList";
    }

    /**
     * 分页查询用户列表
     * @return ok/fail
     */
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value="quartz:getList")
    public PageDataResult getList(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit, ScheduleJobDTO scheduleJobDTO) {
        logger.debug("分页查询用户列表！搜索条件：userSearch：" + scheduleJobDTO + ",page:" + page
                + ",每页记录数量limit:" + limit);
//		ErrorController
        PageDataResult pdr = new PageDataResult();
        try {
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            // 获取用户和角色列表
            pdr = scheduleJobService.getListByPage(scheduleJobDTO, page, limit);
            logger.debug("用户列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户列表查询异常！", e);
        }
        return pdr;
    }
    @RequestMapping("/save")
    @ResponseBody
    public ResultData save(ScheduleJob job) {
        List<ScheduleJob> list = scheduleJobService.select(job);
       if(job.getJobId() == null){
           if(!CollectionUtils.isEmpty(list)){
                return new ResultData("0","任务组和任务名称已存在");
           }
           job.setJobStatus(ScheduleJob.STATUS_NOT_JOB);
           job.setCreateTime(new Date());
           job.setUpdateTime(new Date());
           scheduleJobService.insert(job);
           return new ResultData("1","新增成功");
       }
        if(!CollectionUtils.isEmpty(list)){
            if(!list.stream().allMatch(scheduleJob -> scheduleJob.getJobId()== job.getJobId())){
                return new ResultData("0","任务组和任务名称已存在");
            }
        }
        job.setUpdateTime(new Date());
        scheduleJobService.updateByPrimaryKeySelective(job);
        return new ResultData("1","修改成功");
    }

    /**
     * 修改jobStatus的
     * @param job
     * @return
     */
    @RequestMapping("/updateJobStatus")
    @ResponseBody
    public int updateJobStatus(ScheduleJob job) {
        ScheduleJob job1 = scheduleJobService.selectByPrimaryKey(job.getJobId());
        if(job1.getJobStatus() == ScheduleJob.STATUS_NOT_JOB && job.getJobStatus() == ScheduleJob.STATUS_JOB){
            if(!InitScheduleTask.validateJob(job1)){  //job验证运行不可行；启用状态失败
                return 0;
            }
        }
        int i = scheduleJobService.updateByPrimaryKeySelective(job);
        try {
            job1.setJobStatus(job.getJobStatus());
            InitScheduleTask.addJob(job1,schedulerFactoryBean);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return 0;
        }
        return i;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Long jobId) throws SchedulerException {
       return scheduleJobService.deleteByPrimaryKey(jobId);
    }

    @RequestMapping("/selectById")
    public ScheduleJob selectById() {
        return scheduleJobService.selectByPrimaryKey(3L);
    }

    @RequestMapping("/selectAll")
    public List<ScheduleJob> selectAll() {
        return null;
    }


    @RequestMapping("/notice")
    public String notice(Long jobId) {
        ScheduleJob job = scheduleJobService.selectByPrimaryKey(jobId);
        try {
            InitScheduleTask.addJob(job,schedulerFactoryBean);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
    @RequestMapping("/test")
    @ResponseBody
    public ResultData test(Long jobId) {
        ScheduleJob job = scheduleJobService.selectByPrimaryKey(jobId);
        try {
            Class clazz = Class.forName(job.getBeanClass());
            Object obj = clazz.newInstance();
            Method method = null;
            if(StringUtils.isEmpty(job.getJobData())){
                method = clazz.getMethod(job.getMethodName());
                method.invoke(obj);
            }else{
                method = clazz.getMethod(job.getMethodName(),String.class);
                method.invoke(obj,job.getJobData());
            }

            new CronExpression(job.getCronExpression());
        } catch (ParseException e) {
            return new ResultData("0","时间正在表达式错误");
        } catch (ClassNotFoundException e) {
            return new ResultData("0","类全路径错误");
        } catch (NoSuchMethodException e) {
            return new ResultData("0","类全路径下方法找不到");
        } catch (IllegalAccessException|InstantiationException|InvocationTargetException e) {
            return new ResultData("0","类实例化和调用访问异常");
        }
        return new ResultData();
    }
}