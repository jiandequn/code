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
public class AppInletCountWeek implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    @ExcelProperty(value = "年份",index=0)
    private String y;

    /**
     * 日期
     */
    @ExcelProperty(value = "周",index=1)
    private String w;

    /**
     * 用户类型
     */
    @ExcelProperty(value = "用户类型",index=2)
    private String userType;

    /**
     * 专区ID
     */
    @ExcelProperty(value = "专区ID",index=3)
    private String parentColumnId;

    /**
     * 用户数
     */
    @ExcelProperty(value = "用户数",index=4)
    private Integer userCount;

    /**
     * 点击次数
     */
    @ExcelProperty(value = "访问次数",index=0)
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
    public String getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(String parentColumnId) {
        this.parentColumnId = parentColumnId;
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

    @Override
    public String toString() {
        return "AppInletCountWeek{" +
            "y=" + y +
            ", w=" + w +
            ", userType=" + userType +
            ", parentColumnId=" + parentColumnId +
            ", userCount=" + userCount +
            ", visitCount=" + visitCount +
        "}";
    }
}
