package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 所有专区按天统计信息
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
public class NoappAreaVisitCountDay implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    private String tDate;

    /**
     * 用户类型
     */
    private String userType;

    private String areaCode;

    /**
     * 访问用户数
     */
    private Long pageUserCount;

    /**
     * 播放用户数
     */
    private Long playUserCount;

    /**
     * 播放次数
     */
    private Long playCount;

    /**
     * 累计播放时长
     */
    private Long duration;

    @TableField(exist = false)
    private String areaName;
    /**
     * 区域新增用户数
     */
    private Long addUserCount;
    /**
     * 区域总访问用户数
     */
    private Long totalUserCount;
    /**
     * 人均点播次数
     */
    @TableField(exist = false)
    private BigDecimal perUserPlayCount;

    public BigDecimal getPerUserPlayCount() {
        return perUserPlayCount;
    }

    public void setPerUserPlayCount(BigDecimal perUserPlayCount) {
        this.perUserPlayCount = perUserPlayCount;
    }

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
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public Long getPageUserCount() {
        return pageUserCount;
    }

    public void setPageUserCount(Long pageUserCount) {
        this.pageUserCount = pageUserCount;
    }
    public Long getPlayUserCount() {
        return playUserCount;
    }

    public void setPlayUserCount(Long playUserCount) {
        this.playUserCount = playUserCount;
    }
    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getAddUserCount() {
        return addUserCount;
    }

    public void setAddUserCount(Long addUserCount) {
        this.addUserCount = addUserCount;
    }

    public Long getTotalUserCount() {
        return totalUserCount;
    }

    public void setTotalUserCount(Long totalUserCount) {
        this.totalUserCount = totalUserCount;
    }

    @Override
    public String toString() {
        return "NoappAreaVisitCountDay{" +
            "tDate=" + tDate +
            ", userType=" + userType +
            ", areaCode=" + areaCode +
            ", pageUserCount=" + pageUserCount +
            ", playUserCount=" + playUserCount +
            ", playCount=" + playCount +
            ", duration=" + duration +
            ", areaName=" + areaName +
        "}";
    }
}
