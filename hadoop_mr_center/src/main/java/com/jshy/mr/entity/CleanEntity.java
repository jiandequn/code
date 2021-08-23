package com.jshy.mr.entity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/7
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class CleanEntity {
    private List<String> userIdList;
    private List<ParentColumnEntity> parentColumnList;
    private String[] valTime;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public List<ParentColumnEntity> getParentColumnList() {
        return parentColumnList;
    }

    public void setParentColumnList(List<ParentColumnEntity> parentColumnList) {
        this.parentColumnList = parentColumnList;
    }

    public String[] getValTime() {
        return valTime;
    }

    public void setValTime(String[] valTime) {
        this.valTime = valTime;
    }
}
