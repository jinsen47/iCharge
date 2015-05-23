package com.icharge.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class LocationBean {
	private String slow_count;
	private String fast_count;
	private String total_count;
	private String name;
	private String location;
	
	public LocationBean(String slow_count, String fast_count,
			String total_count, String name, String location) {
		super();
		this.slow_count = slow_count;
		this.fast_count = fast_count;
		this.total_count = total_count;
		this.name = name;
		this.location = location;
	}

	public LocationBean(){}
	
	public String getSlow_count() {
		return slow_count;
	}
	public void setSlow_count(String slow_count) {
		this.slow_count = slow_count;
	}
	public String getFast_count() {
		return fast_count;
	}
	public void setFast_count(String fast_count) {
		this.fast_count = fast_count;
	}
	public String getTotal_count() {
		return total_count;
	}
	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
