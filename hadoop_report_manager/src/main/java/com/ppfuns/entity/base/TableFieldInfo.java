package com.ppfuns.entity.base;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/28
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
public class TableFieldInfo {
    private String columnName;
    private String columnComment;

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
}
