package com.ppfuns.entity;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/17
 * Time: 16:43
 * To change this template use File | Settings | File Templates.
 */
public class SearchDate {
    private String dateType; //查询类型

    private String tableName; //查询表

    private String startTime;
    private String endTime;
    private String year;
    private String month;
    private String week;
    private String parentColumnId;
    private String userType;

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getTableName() {
        return tableName.concat("_").concat(dateType);
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(String parentColumnId) {
        this.parentColumnId = parentColumnId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
