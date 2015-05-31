package com.icharge.beans;

import java.util.List;

public class LocationListBean {
	private String city;
	private List<LocationBean> list;

	public LocationListBean(String city, List<LocationBean> list) {
		this.city = city;
		this.list = list;
	}

	public LocationListBean(){}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<LocationBean> getList() {
		return list;
	}
	public void setList(List<LocationBean> list) {
		this.list = list;
	}
}
