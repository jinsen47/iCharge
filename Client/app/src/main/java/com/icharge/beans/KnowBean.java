package com.icharge.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class KnowBean {
	//知识库项
	private String title;
	private String image;
	private String articleURL;
	
	public KnowBean(String title, String image, String articleURL) {
		super();
		this.title = title;
		this.image = image;
		this.articleURL = articleURL;
	}

	public KnowBean(){}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getArticleURL() {
		return articleURL;
	}
	public void setArticleURL(String articleURL) {
		this.articleURL = articleURL;
	}
	
	
}
