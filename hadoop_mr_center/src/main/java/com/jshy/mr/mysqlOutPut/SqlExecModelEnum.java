package com.jshy.mr.mysqlOutPut;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/22
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public enum SqlExecModelEnum {
    INSERT("insert","单纯插入"),
    REPLACE("replace","替换最新"),
    INSERT_NO_EXISTS("insertNoExist","插入并且不存在是");
    private String name;
    private String text;

    SqlExecModelEnum(String name,String text){
        this.name = name;
        this.text = text;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
