package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 所有专区类型下专辑播放次数月排行榜(NoappContentTypeAlbumPlayCountRankMonth)表实体类
 *
 * @author jdq
 * @since 2021-07-06 17:03:14
 */
@SuppressWarnings("serial")
public class NoappContentTypeAlbumPlayCountRankMonth extends Model<NoappContentTypeAlbumPlayCountRankMonth> {

    private Integer y;
    //月
    private Object m;

    private String contentType;

    private String contentTypeName;

    private Integer albumId;

    private String albumName;

    private Long playCount;

    private Integer rank;
    private String userType;
    private Long userCount;
    private Long duration;

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
