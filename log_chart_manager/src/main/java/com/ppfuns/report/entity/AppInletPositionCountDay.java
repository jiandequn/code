package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 用户来源按日统计(AppInletPositionCountDay)表实体类
 *
 * @author jdq
 * @since 2021-05-25 09:56:21
 */
@SuppressWarnings("serial")
public class AppInletPositionCountDay implements Serializable {
    //日期
    private String tDate;
    //用户类型
    private String userType;
    //事件
    private String eventsType;
    //专区ID
    private String parentColumnId;
    //spm
    private String spm;
    //用户数
    private Integer userCount;
    //点击次数
    private Long visitCount;

    private String contentName;
    public String gettDate() {
        return tDate;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEventsType() {
        return eventsType;
    }

    public void setEventsType(String eventsType) {
        this.eventsType = eventsType;
    }

    public String getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(String parentColumnId) {
        this.parentColumnId = parentColumnId;
    }

    public String getSpm() {
        return spm;
    }

    public void setSpm(String spm) {
        this.spm = spm;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }
}
