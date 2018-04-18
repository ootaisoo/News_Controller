package com.example.administrator.news_controller.model;

import com.example.administrator.news_controller.NewsItem;

import java.util.List;

public interface INewsFromDbLoader {
    void loadNewsFromDb(NewsFromDbLoader.NewsListener listener);
    void saveNewsToDb(List<NewsItem> news);
}
