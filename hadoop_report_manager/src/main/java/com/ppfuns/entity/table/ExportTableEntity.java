package com.ppfuns.entity.table;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/19
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class ExportTableEntity {
    private Integer id;
    private Integer tableId;
    private String name;
    private String querySql;
    private String fileFormat;
    private Integer isEffective;
    private Date createTime;
    private Integer isPage;

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
}
