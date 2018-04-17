package com.example.administrator.news_controller;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Content extends RealmObject {

    @SerializedName("body")
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
