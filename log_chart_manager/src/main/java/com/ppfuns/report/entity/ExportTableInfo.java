package com.ppfuns.report.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 导出table维护信息
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-20
 */
public class ExportTableInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer tableId;

    /**
     * 导出文件格式名称
     */
    private String name;

    private String querySql;

    /**
     * 格式
     */
    private String fileFormat;

    private Integer isEffective;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }
    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }
    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ExportTableInfo{" +
            "tableId=" + tableId +
            ", name=" + name +
            ", querySql=" + querySql +
            ", fileFormat=" + fileFormat +
            ", isEffective=" + isEffective +
            ", createTime=" + createTime +
        "}";
    }
}
