package com.example.administrator.news_controller.model.entities;

import com.example.administrator.news_controller.model.entities.Content;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Root extends RealmObject {

    @PrimaryKey
    @SerializedName("url")
    private String url;

    @SerializedName("share_image")
    private String shareImage;

    @SerializedName("content")
    private Content content;

    @SerializedName("title")
    private String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
