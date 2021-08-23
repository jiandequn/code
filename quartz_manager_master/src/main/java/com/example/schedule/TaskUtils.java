package com.example.schedule;

import com.example.model.ScheduleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @模块名称：批跑自动化
 * @Description task工具类反射调用
 * @version：1.0
 * @author：cc
 * @date：2018年5月15日 上午10:42:04
 */
public class TaskUtils {
    private final static Logger logger = LoggerFactory.getLogger(TaskUtils.class);
    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    @SuppressWarnings("unchecked")
    public static void invokMethod(ScheduleJob scheduleJob) {
        Object object = null;
        Class clazz = null;
        boolean flag = true;
        // 1.获取bean
        // beanId不为空则通过SpringUtils获取
        if (!StringUtils.isEmpty(scheduleJob.getSpringId())) {
            object = SpringUtils.getBean(scheduleJob.getSpringId());
        }else if (!StringUtils.isEmpty(scheduleJob.getBeanClass())) {  // beanId为空则通过包名+类名完整路径反射获得
            try {
                clazz = Class.forName(scheduleJob.getBeanClass());
                object = clazz.newInstance();
            }catch (Exception e) {
                flag = false;
                logger.error("未找到" + scheduleJob.getBeanClass() + "对应的class", e);
            }

        }
        if (object == null) {
            flag = false;
            logger.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查是否配置正确！！！");
            return;
        }
        clazz = object.getClass();
        Method method = null;
        // 2.获取方法
        try {
            // 判断参数是否为空
            if (StringUtils.isEmpty(scheduleJob.getJobData())) {
                method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
            }
            else {
                method = clazz.getDeclaredMethod(scheduleJob.getMethodName(), new Class[] { String.class });
            }
        }catch (NoSuchMethodException e) {
            flag = false;
            logger.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！", e.getCause());
        }catch (SecurityException e) {
            logger.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！", e);
        }
        if (method != null) {            // 3.执行方法
            try {
                // 根据参数执行方法
                if (StringUtils.isEmpty(scheduleJob.getJobData())) {
                    method.invoke(object);
                    logger.info("执行方法"+method.getName()+"，无参数");
                }else {
                    method.invoke(object, scheduleJob.getJobData());
                    logger.info("执行方法："+method.getName()+"，参数："+scheduleJob.getJobData());
                }
            }catch (IllegalAccessException e) {
                flag = false;
                logger.error("未找到" + scheduleJob.getBeanClass() + "类下" + scheduleJob.getMethodName() + "对应的方法参数设置错误", e);
            }catch (IllegalArgumentException e) {
                flag = false;
                logger.error("未找到" + scheduleJob.getBeanClass() + "类下" + scheduleJob.getMethodName() + "对应的方法参数设置错误", e);
            } catch (InvocationTargetException e) {
                flag = false;
                logger.error("未找到" + scheduleJob.getBeanClass() + "类下" + scheduleJob.getMethodName() + "对应的方法参数设置错误", e);
            }
        }
        if (flag) {
            logger.info("任务名称 = [" + scheduleJob.getJobName() + "]----------启动成功");
        }

    }
}
