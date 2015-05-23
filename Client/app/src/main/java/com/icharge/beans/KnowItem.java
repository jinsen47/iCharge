package com.icharge.beans;

/**
 * Created by Jinsen on 15/5/23.
 */
public class KnowItem {
    private String title;
    private String image;
    private String articleURL;

    public KnowItem(String title, String image, String articleURL) {
        this.title = title;
        this.image = image;
        this.articleURL = articleURL;
    }
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
