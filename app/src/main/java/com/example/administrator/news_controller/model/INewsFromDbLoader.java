package com.example.administrator.news_controller.model;

public interface INewsFromDbLoader {
    void loadNewsFromDb(NewsFromDbLoader.NewsListener listener);
}
