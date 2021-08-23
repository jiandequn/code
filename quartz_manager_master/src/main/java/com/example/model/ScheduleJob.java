package com.example.model;

import java.util.Date;

public class ScheduleJob {
    public static final String STATUS_RUNNING = "1"; // 正在运行

    public static final Byte STATUS_NOT_RUNNING = 0; // 已停止

    public static final Byte CONCURRENT_IS = 1;// 异步，可多个同时执行

    public static final Byte CONCURRENT_NOT = 0;// 同步，需排队执行

    public static final Byte STATUS_JOB = 1; // 有效运行

    public static final Byte STATUS_NOT_JOB = 0; // 无效不运行
    private Long jobId;

    private String jobName;

    private String jobGroup;

    private Byte isConcurrent;

    private String cronExpression;

    private String springId;

    private String beanClass;

    private String methodName;

    private Byte runingStatus;

    private Byte jobStatus;

    private Date createTime;

    private Date updateTime;

    private String jobData;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }

    public Byte getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(Byte isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression == null ? null : cronExpression.trim();
    }

    public String getSpringId() {
        return springId;
    }

    public void setSpringId(String springId) {
        this.springId = springId == null ? null : springId.trim();
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass == null ? null : beanClass.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public Byte getRuningStatus() {
        return runingStatus;
    }

    public void setRuningStatus(Byte runingStatus) {
        this.runingStatus = runingStatus;
    }

    public Byte getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Byte jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getJobData() {
        return jobData;
    }

    public void setJobData(String jobData) {
        this.jobData = jobData;
    }

    private Byte isEffective;

    public Byte getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Byte isEffective) {
        this.isEffective = isEffective;
    }
}