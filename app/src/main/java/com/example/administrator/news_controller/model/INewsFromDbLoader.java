package com.example.administrator.news_controller.model;

import com.example.administrator.news_controller.News;

public interface INewsFromDbLoader {
    void loadNewsFromDb(NewsFromDbLoader.NewsListener listener);
    void saveNewsToDb(News news);
}
