package com.example.administrator.news_controller;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class DetailedNews extends RealmObject {

    @SerializedName("root")
    private Root root;

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
