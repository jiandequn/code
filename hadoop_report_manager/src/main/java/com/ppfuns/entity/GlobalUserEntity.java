package com.ppfuns.entity;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/17
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
public class GlobalUserEntity extends DateEntity{
    private String userCount;

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }
}
