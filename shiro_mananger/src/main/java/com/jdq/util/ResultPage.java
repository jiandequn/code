package com.jdq.util;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/17
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class ResultPage<T> {
    private String status = "200";
    private List<T> rows= new ArrayList<T>();
    private Integer total = 0;
    public static ResultPage fromMybatisPlusPage(IPage page){
        return new ResultPage((int) page.getTotal(),page.getRecords());
    }
    public ResultPage(){};
    public ResultPage(Integer total,List rows){
        this.total = total;
        this.rows = rows;
    }
    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
