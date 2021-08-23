package com.example.utils;

/**
 *
 *日志级别定义
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/6/4
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public enum LogLevelEnum {
    INFO((byte)1,"INFO"),
    WARRING((byte)2,"WARRING"),
    ERROR((byte)3,"ERROR"),;

    private byte id;
    private String name;
    LogLevelEnum(byte id, String name){
        this.id=id;
        this.name = name;
    }
    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
