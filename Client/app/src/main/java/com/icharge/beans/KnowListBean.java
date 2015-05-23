package com.icharge.beans;

import java.util.List;

public class KnowListBean {
	private String count;
	private List<KnowBean> list;

	public KnowListBean(String count, List<KnowBean> list) {
		this.count = count;
		this.list = list;
	}

	public KnowListBean(){}

	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<KnowBean> getList() {
		return list;
	}
	public void setList(List<KnowBean> list) {
		this.list = list;
	}
}
