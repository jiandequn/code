package com.ppfuns.utils;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/26
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
public class ComboxEntity {
    private String id;
    private String text;
    public ComboxEntity(){

    }
    public ComboxEntity(String keyVal){
        String[] t = keyVal.split(":");
        this.id = t[0];
        this.text = t[1];
    }
    public ComboxEntity(String id,String text){
        this.id = id;
        this.text = text;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
