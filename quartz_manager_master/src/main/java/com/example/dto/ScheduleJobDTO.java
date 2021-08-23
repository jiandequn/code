package com.example.dto;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/5/28
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleJobDTO {
    private Integer page;

    private Integer limit;

    private String jobGroup;

    private String jobName;

    private String insertTimeStart;

    private String insertTimeEnd;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getInsertTimeStart() {
        return insertTimeStart;
    }

    public void setInsertTimeStart(String insertTimeStart) {
        this.insertTimeStart = insertTimeStart;
    }

    public String getInsertTimeEnd() {
        return insertTimeEnd;
    }

    public void setInsertTimeEnd(String insertTimeEnd) {
        this.insertTimeEnd = insertTimeEnd;
    }
}
