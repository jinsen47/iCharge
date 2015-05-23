package com.icharge.beans;

import java.util.List;

public class LocationListBean {
	private String count;
	private List<LocationBean> list;

	public LocationListBean(String count, List<LocationBean> list) {
		this.count = count;
		this.list = list;
	}

	public LocationListBean(){}

	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<LocationBean> getList() {
		return list;
	}
	public void setList(List<LocationBean> list) {
		this.list = list;
	}
}
