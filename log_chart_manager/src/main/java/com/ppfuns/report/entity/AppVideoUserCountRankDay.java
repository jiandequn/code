package com.ppfuns.report.entity;

import java.io.Serializable;

/**
 * <p>
 * 专辑播放用户数排行榜
 * </p>
 *
 * @author jian.dq
 * @since 2021-01-13
 */
public class AppVideoUserCountRankDay implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tDate;

    private String parentColumnId;

    private Integer albumId;

    private String albumName;

    private Integer contentType;

    private String contentTypeName;

    private Integer videoId;

    private String videoName;

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
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
    public String gettDate() {
        return tDate;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
    }
    public String getParentColumnId() {
        return parentColumnId;
    }

    public void setParentColumnId(String parentColumnId) {
        this.parentColumnId = parentColumnId;
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
    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }
    public String getContentTypeName() {
        return contentTypeName;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }
    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }
    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    @Override
    public String toString() {
        return "AppVideoUserCountRankDay{" +
            "tDate=" + tDate +
            ", parentColumnId=" + parentColumnId +
            ", albumId=" + albumId +
            ", albumName=" + albumName +
            ", contentType=" + contentType +
            ", contentTypeName=" + contentTypeName +
            ", videoId=" + videoId +
            ", videoName=" + videoName +
            ", userCount=" + userCount +
        "}";
    }
}
