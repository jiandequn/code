package com.ppfuns.report.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * <p>
 * 按周统计详情访问
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-19
 */
public class AppDetailPageCountWeek implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年份
     */
    @ExcelProperty(value = "年份",index=0)
    private Integer y;

    /**
     * 周
     */
    @ExcelProperty(value = "周",index=1)
    private Integer w;

    /**
     * 专区ID
     */
    @ExcelProperty(value = "专区ID",index=2)
    private String parentColumnId;

    /**
     * 用户类型
     */
    @ExcelProperty(value = "用户类型",index=3)
    private String userType;

    /**
     * 用户数
     */
    @ExcelProperty(value = "访问用户数",index=4)
    private Long userCount;

    /**
     * 点击数
     */
    @ExcelProperty(value = "访问次数",index=5)
    private Long visitCount;

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
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
    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
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
        return "AppDetailPageCountWeek{" +
            "y=" + y +
            ", w=" + w +
            ", parentColumnId=" + parentColumnId +
            ", userType=" + userType +
            ", userCount=" + userCount +
            ", visitCount=" + visitCount +
        "}";
    }
}
