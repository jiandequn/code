package com.ppfuns.report.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * <p>
 * 现网产品统计
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
public class ProductColumn implements Serializable {


    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 栏目ID
     */
    private String columnId;

    /**
     * 栏目名称
     */
    private String columnName;

    /**
     * 1有效 0禁用
     */
    private Boolean isEffective;
    /**
     * VOD OTT
     */
    private String userType;

    private Date startDate;
    private Date endDate;
    private Integer seq;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    public Boolean getEffective() {
        return isEffective;
    }

    public void setEffective(Boolean isEffective) {
        this.isEffective = isEffective;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    public Date getStartDate() {
        return startDate;
    }
    @DateTimeFormat(pattern="yyyy-MM-dd")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    public Date getEndDate() {
        return endDate;
    }
    @DateTimeFormat(pattern="yyyy-MM-dd")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "ProductColumn{" +
            "columnId=" + columnId +
            ", columnName=" + columnName +
            ", isEffective=" + isEffective +
        "}";
    }
}
