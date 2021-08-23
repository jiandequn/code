package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 所有专区类型下专辑播放用户数周排行榜(NoappContentTypeAlbumUserCountRankWeek)表实体类
 *
 * @author jdq
 * @since 2021-07-06 17:03:15
 */
@SuppressWarnings("serial")
public class NoappContentTypeAlbumUserCountRankWeek extends Model<NoappContentTypeAlbumUserCountRankWeek> {

    private Integer y;
    //周
    private Object w;

    private String contentType;

    private String contentTypeName;

    private Integer albumId;

    private String albumName;

    private Long userCount;

    private Integer rank;

    private String userType;
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

    public Object getW() {
        return w;
    }

    public void setW(Object w) {
        this.w = w;
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
