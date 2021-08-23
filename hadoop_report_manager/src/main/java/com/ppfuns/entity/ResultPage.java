package com.ppfuns.entity;

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
    private List<T> rows= new ArrayList<T>();
    private Integer total = 0;
    private List<T> footer = new ArrayList<>();
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

    public List<T> getFooter() {
        return footer;
    }

    public void setFooter(List<T> footer) {
        this.footer = footer;
    }
}
