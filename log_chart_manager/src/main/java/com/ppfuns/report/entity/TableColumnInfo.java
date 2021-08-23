package com.ppfuns.report.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-20
 */
public class TableColumnInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "column_id", type = IdType.AUTO)
    private Integer columnId;

    private Integer exportTableId;

    private String columnName;

    private String columnComment;

    private Integer isEffective;

    /**
     * 顺序
     */
    private Integer seq;

    private Date createTime;

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }
    public Integer getExportTableId() {
        return exportTableId;
    }

    public void setExportTableId(Integer exportTableId) {
        this.exportTableId = exportTableId;
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

    @Override
    public String toString() {
        return "TableColumnInfo{" +
            "columnId=" + columnId +
            ", exportTableId=" + exportTableId +
            ", columnName=" + columnName +
            ", columnComment=" + columnComment +
            ", isEffective=" + isEffective +
            ", seq=" + seq +
            ", createTime=" + createTime +
        "}";
    }
}
