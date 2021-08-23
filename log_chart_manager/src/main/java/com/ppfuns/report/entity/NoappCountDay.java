package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 所有专区日统计(NoappCountDay)表实体类
 *
 * @author jdq
 * @since 2021-07-12 17:28:09
 */
@SuppressWarnings("serial")
public class NoappCountDay extends Model<NoappCountDay> {
    //日期
    private String tDate;
    //用户类型VOD|OTT
    private String userType;
    //访问用户数
    private Long visitUserCount;
    //播放用户数
    private Long playUserCount;
    //播放次数
    private Long playCount;
    //播放时长
    private Long duration;
    //新增用户数
    private Long addUserCount;
    //累计访问用户数
    private Long totalVisitUserCount;

    @TableField(value="user_2day_count",exist = false)
    private Integer user2dayCount;
    //3日留存用户数
    @TableField(value="user_3day_count",exist = false)
    private Integer user3dayCount;
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

    public Long getVisitUserCount() {
        return visitUserCount;
    }

    public void setVisitUserCount(Long visitUserCount) {
        this.visitUserCount = visitUserCount;
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

    public Long getAddUserCount() {
        return addUserCount;
    }

    public void setAddUserCount(Long addUserCount) {
        this.addUserCount = addUserCount;
    }

    public Long getTotalVisitUserCount() {
        return totalVisitUserCount;
    }

    public void setTotalVisitUserCount(Long totalVisitUserCount) {
        this.totalVisitUserCount = totalVisitUserCount;
    }

    public Integer getUser2dayCount() {
        return user2dayCount;
    }

    public void setUser2dayCount(Integer user2dayCount) {
        this.user2dayCount = user2dayCount;
    }

    public Integer getUser3dayCount() {
        return user3dayCount;
    }

    public void setUser3dayCount(Integer user3dayCount) {
        this.user3dayCount = user3dayCount;
    }
}
