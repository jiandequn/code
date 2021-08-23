package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 所有专区类型下专辑播放时长周排行榜(NoappContentTypeAlbumDurationRankWeek)表实体类
 *
 * @author jdq
 * @since 2021-07-14 15:21:59
 */
@SuppressWarnings("serial")
public class NoappContentTypeAlbumDurationRankWeek extends Model<NoappContentTypeAlbumDurationRankWeek> {

    private Integer y;
    //周
    private Object w;
    //用户类型
    private String userType;

    private String contentType;

    private String contentTypeName;

    private Integer albumId;

    private String albumName;

    private Long duration;

    private Integer rank;
    private Long userCount;
    private Long playCount;

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Object getW() {
        return w;
    }

    public void setW(Object w) {
        this.w = w;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
