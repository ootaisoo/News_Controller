package com.example.administrator.news_controller.model.loaders;

import com.example.administrator.news_controller.model.entities.NewsItem;

import java.util.List;

public interface INewsFromDbLoader {
    void loadNewsFromDb(NewsFromDbLoader.NewsListener listener);
    void saveNewsToDb(List<NewsItem> news);
}
