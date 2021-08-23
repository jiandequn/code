package com.ppfuns.entity;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/17
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
public class SearchPage<T> {
    private int page;    //第几页
    private int rows;   //多少行
    private T t;  //参数对象
    private int pageSize=30; //获取多少数据
    private int pageIndex=0;//mysql从多少开始获取

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }
    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
        this.setPageIndex(page);
    }
    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }
    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
        this.pageSize=rows;
    }
    /**
     * @return the pageIndex
     */
    public int getPageIndex() {
        return pageIndex*this.pageSize;
    }
    /**
     * @param pageIndex the pageIndex to set
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex-1<0?0:pageIndex-1;
    }
    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }
    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
