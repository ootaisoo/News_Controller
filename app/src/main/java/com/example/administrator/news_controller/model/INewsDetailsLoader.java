package com.example.administrator.news_controller.model;

public interface INewsDetailsLoader {
    void loadDetails(String newsPath, final NewsDetailsLoader.DetailsListener listener);
}
