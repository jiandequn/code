package com.ppfuns.report.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * <p>
 * 专区搜索专辑按周排行榜
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-19
 */
public class AppSearchAlbumRankWeek implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年
     */
    @ExcelProperty(value = "年份",index=0)
    private Integer y;

    /**
     * 周
     */
    @ExcelProperty(value = "周",index=1)
    private Integer w;

    /**
     * 栏目产品ID
     */
    @ExcelProperty(value = "专区ID",index=2)
    private String parentColumnId;

    /**
     * 专辑ID
     */
    @ExcelProperty(value = "专辑ID",index=3)
    private Integer albumId;

    /**
     * 专辑名称
     */
    @ExcelProperty(value = "专辑名称",index=4)
    private String albumName;

    /**
     * 专辑内容类型
     */
    @ExcelProperty(value = "类型",index=5)
    private Integer contentType;

    /**
     * 专辑内容名称
     */
    @ExcelProperty(value = "类型名称",index=6)
    private String contentTypeName;

    /**
     * 统计访问量
     */
    @ExcelProperty(value = "搜索次数",index=7)
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
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AppSearchAlbumRankWeek{" +
            "y=" + y +
            ", w=" + w +
            ", parentColumnId=" + parentColumnId +
            ", albumId=" + albumId +
            ", albumName=" + albumName +
            ", contentType=" + contentType +
            ", contentTypeName=" + contentTypeName +
            ", count=" + count +
        "}";
    }
}
