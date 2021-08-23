package com.jdq.util;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/3/3
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
public class ResultData {
    private String code="1";
    private Object result;
    private String msg="操作成功";

    public ResultData(){}
    public ResultData(String msg){
        this.msg = msg;
    }
    public ResultData(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public ResultData(String code, String msg, Object result){
        this.code = code;
        this.msg = msg;
        this.result = result;
    }
    public ResultData(String code, Object result){
        this.code = code;
        this.result = result;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
