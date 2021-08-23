package com.jshy.mr.entity.base;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/30
 * Time: 18:04
 * To change this template use File | Settings | File Templates.
 */
public class ResultData<T> {
    private String code="1";
    private String msg="成功";
    private T data;
    public ResultData(){}
    public ResultData(String code, String msg){
        this.code=code;
        this.msg = msg;
    }
    public ResultData(String code, String msg, T data){
        this.code=code;
        this.msg = msg;
        this.data = data;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
