package com.example.administrator.news_controller.model;

import com.example.administrator.news_controller.News;

import io.reactivex.Single;

public interface INewsLoader {
    Single<News> getNewsSingle();
}
