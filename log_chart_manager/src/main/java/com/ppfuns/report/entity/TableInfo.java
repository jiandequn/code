package com.ppfuns.report.entity;

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
public class TableInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String tableName;

    private String tableComment;

    private Integer isEffective;

    /**
     * 更新sql
     */
    private String updateSql;

    private Date createTime;

    /**
     * -1已删除
     */
    private Integer delFlag;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }
    public String getUpdateSql() {
        return updateSql;
    }

    public void setUpdateSql(String updateSql) {
        this.updateSql = updateSql;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
            "tableName=" + tableName +
            ", tableComment=" + tableComment +
            ", isEffective=" + isEffective +
            ", updateSql=" + updateSql +
            ", createTime=" + createTime +
            ", delFlag=" + delFlag +
        "}";
    }
}
