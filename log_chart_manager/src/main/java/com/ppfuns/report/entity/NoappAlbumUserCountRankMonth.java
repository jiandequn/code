package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 所有专区专辑播放用户数月排行榜(NoappAlbumUserCountRankMonth)表实体类
 *
 * @author jdq
 * @since 2021-07-08 11:39:39
 */
@SuppressWarnings("serial")
public class NoappAlbumUserCountRankMonth extends Model<NoappAlbumUserCountRankMonth> {

    private Integer y;
    //月
    private Object m;
    //用户类型
    private String userType;

    private Integer albumId;

    private String albumName;

    private String contentType;

    private String contentTypeName;

    private Long userCount;

    private Integer rank;
    private Long playCount;
    private Long duration;

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


    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Object getM() {
        return m;
    }

    public void setM(Object m) {
        this.m = m;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentTypeName() {
        return contentTypeName;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
