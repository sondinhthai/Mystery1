package com.example.mystery1.models;

public class Documents {
    private String title;
    private String content;
    private String imgUrl;

    public Documents() {
    }

    public Documents(String title, String content, String imgUrl) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
