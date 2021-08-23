package com.ppfuns.report.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 专区按周统计信息
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
public class AppAreaVisitCountWeek implements Serializable {

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
     * 区域码
     */
    @ExcelProperty(value = "区域码",index=4)
    private String areaCode;
    @TableField(exist = false)
    private String areaName;

    /**
     * 访问用户数
     */
    @ExcelProperty(value = "访问用户数",index=5)
    private Integer pageUserCount;

    /**
     * 播放用户数
     */
    @ExcelProperty(value = "播放用户数",index=6)
    private Integer playUserCount;

    /**
     * 播放次数
     */
    @ExcelProperty(value = "点播次数",index=7)
    private Long playCount;

    /**
     * 累计播放时长
     */
    @ExcelProperty(value = "播放时长",index=8)
    private Long duration;


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
        return "AppVisitCountWeek{" +
            "y=" + y +
            ", w=" + w +
            ", parentColumnId=" + parentColumnId +
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
