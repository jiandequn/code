package com.ppfuns.report.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * <p>
 * 专辑播放次数排行榜
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-19
 */
public class AppVideoPlayCountRankWeek implements Serializable {

    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "年份",index=0)
    private Integer y;
    @ExcelProperty(value = "周",index=1)
    private Integer w;
    @ExcelProperty(value = "专区ID",index=2)
    private String parentColumnId;
    @ExcelProperty(value = "专辑ID",index=3)
    private Integer albumId;
    @ExcelProperty(value = "专辑名称",index=4)
    private String albumName;
    @ExcelProperty(value = "类型",index=5)
    private Integer contentType;
    @ExcelProperty(value = "类型名称",index=6)
    private String contentTypeName;
    @ExcelProperty(value = "剧集ID",index=7)
    private Integer videoId;
    @ExcelProperty(value = "剧集名称",index=8)
    private String videoName;
    @ExcelProperty(value = "播放次数",index=9)
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
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
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
    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    @Override
    public String toString() {
        return "AppVideoPlayCountRankWeek{" +
            "y=" + y +
            ", w=" + w +
            ", parentColumnId=" + parentColumnId +
            ", albumId=" + albumId +
            ", albumName=" + albumName +
            ", contentType=" + contentType +
            ", contentTypeName=" + contentTypeName +
            ", videoId=" + videoId +
            ", videoName=" + videoName +
            ", playCount=" + playCount +
        "}";
    }
}
