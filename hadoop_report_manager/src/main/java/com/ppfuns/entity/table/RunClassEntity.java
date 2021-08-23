package com.ppfuns.entity.table;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/12/27
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class RunClassEntity {
    private Integer id;
    private String name;
    private String title;
    private Integer runType;
    private Integer isEffective;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRunType() {
        return runType;
    }

    public void setRunType(Integer runType) {
        this.runType = runType;
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
