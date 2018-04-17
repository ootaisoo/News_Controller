package com.example.administrator.news_controller.model;

public interface INewsDetailsFromDBLoader {
    void loadNewsDetailsFromDb(String newsPath, NewsDetailsFromDBLoader.DetailsFromDbListener listener);
}
