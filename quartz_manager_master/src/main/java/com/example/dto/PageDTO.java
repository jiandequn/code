package com.example.dto;

public class PageDTO<T> {
	private Integer pageNo;
	private Integer pageSize;
	private T t;
	private int pageIndex ;

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}
	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex-1<0?0:pageIndex-1;
	}

	public PageDTO(T t, Integer pageNo, Integer pageSize) {
		this.pageNo = pageNo-1 < 0?0:(pageNo-1);
		this.pageSize = pageSize<0?0:pageSize;
		this.pageIndex = this.pageNo* this.pageSize;
		this.t = t;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo-1 < 0?0:(pageNo-1);
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize<0?0:pageSize;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
}
