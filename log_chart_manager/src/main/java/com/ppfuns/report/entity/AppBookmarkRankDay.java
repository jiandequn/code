package com.ppfuns.report.entity;

import java.io.Serializable;

/**
 * <p>
 * 专区按日收藏排行榜
 * </p>
 *
 * @author jian.dq
 * @since 2021-01-13
 */
public class AppBookmarkRankDay implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    private String tDate;

    /**
     * 栏目产品ID
     */
    private String parentColumnId;

    /**
     * 专辑ID
     */
    private Integer albumId;

    /**
     * 专辑名称
     */
    private String albumName;

    /**
     * 专辑内容类型
     */
    private Integer contentType;

    /**
     * 专辑内容名称
     */
    private String contentTypeName;

    /**
     * 统计访问量
     */
    private Integer count;
    private Integer rank;
    private String userType;

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
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AppBookmarkRankDay{" +
            "tDate=" + tDate +
            ", parentColumnId=" + parentColumnId +
            ", albumId=" + albumId +
            ", albumName=" + albumName +
            ", contentType=" + contentType +
            ", contentTypeName=" + contentTypeName +
            ", count=" + count +
        "}";
    }
}
