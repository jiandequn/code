package com.ppfuns.report.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * <p>
 * 用户来源按日统计
 * </p>
 *
 * @author jian.dq
 * @since 2020-12-28
 */
public class AppInletPositionCountWeek implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年份
     */
    @ExcelProperty(value = "年份",index=0)
    private String y;

    /**
     * 周
     */
    @ExcelProperty(value = "周",index=1)
    private String w;

    /**
     * 用户类型
     */
    @ExcelProperty(value = "用户类型",index=2)
    private String userType;

    /**
     * 事件
     */
    @ExcelProperty(value = "事件",index=3)
    private String eventsType;

    /**
     * 专区ID
     */
    @ExcelProperty(value = "专区ID",index=4)
    private String parentColumnId;

    /**
     * spm
     */
    @ExcelProperty(value = "入口配置",index=5)
    private String spm;

    private String contentName;
    /**
     * 用户数
     */
    @ExcelProperty(value = "用户数",index=6)
    private Integer userCount;

    /**
     * 点击次数
     */
    @ExcelProperty(value = "访问次数",index=7)
    private Long visitCount;

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
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

    @Override
    public String toString() {
        return "AppInletPositionCountWeek{" +
            "y=" + y +
            ", w=" + w +
            ", userType=" + userType +
            ", eventsType=" + eventsType +
            ", parentColumnId=" + parentColumnId +
            ", spm=" + spm +
            ", userCount=" + userCount +
            ", visitCount=" + visitCount +
        "}";
    }
}
