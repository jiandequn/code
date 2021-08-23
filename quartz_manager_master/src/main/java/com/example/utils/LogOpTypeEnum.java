package com.example.utils;

/**
 * 日志类型
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/6/4
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public enum LogOpTypeEnum {
    LOGIN((byte)1,"登陆"),
    LOGINOUT((byte)2,"退出"),
    INSERT((byte)3,"插入"),
    DELETE((byte)4,"删除"),
    UPDATE((byte)5,"更新"),
    UPLOAD((byte)6,"上传"),
    OTHER((byte)7,"其他");
    private byte id;
    private String name;
    LogOpTypeEnum(byte id,String name){
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
