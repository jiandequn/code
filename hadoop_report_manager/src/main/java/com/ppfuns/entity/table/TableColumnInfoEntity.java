package com.ppfuns.entity.table;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/20
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class TableColumnInfoEntity {
    private Integer columnId;
    private Integer exportTableId;
    private String columnName;
    private String columnComment;
    private Integer isEffective;
    private Integer seq;
    private Date createTime;

    public Integer getExportTableId() {
        return exportTableId;
    }

    public void setExportTableId(Integer exportTableId) {
        this.exportTableId = exportTableId;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
