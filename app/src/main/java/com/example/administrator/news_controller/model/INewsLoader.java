package com.example.administrator.news_controller.model;

import com.example.administrator.news_controller.News;

import io.reactivex.Single;

public interface INewsLoader {
    NewsLoader.NewsService getNewsService();
    void saveToDb(News news);
}
