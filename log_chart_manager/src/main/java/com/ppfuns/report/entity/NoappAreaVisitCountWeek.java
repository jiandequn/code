package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 所有专区按周统计信息
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
public class NoappAreaVisitCountWeek implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年份
     */
    private String y;

    /**
     * 周
     */
    private String w;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 区域码
     */
    private String areaCode;

    /**
     * 访问用户数
     */
    private Integer pageUserCount;

    /**
     * 播放用户数
     */
    private Integer playUserCount;

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
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public Integer getPageUserCount() {
        return pageUserCount;
    }

    public void setPageUserCount(Integer pageUserCount) {
        this.pageUserCount = pageUserCount;
    }
    public Integer getPlayUserCount() {
        return playUserCount;
    }

    public void setPlayUserCount(Integer playUserCount) {
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

    @Override
    public String toString() {
        return "NoappAreaVisitCountWeek{" +
            "y=" + y +
            ", w=" + w +
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
