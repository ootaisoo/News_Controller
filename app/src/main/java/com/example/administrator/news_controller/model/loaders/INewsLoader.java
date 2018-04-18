package com.example.administrator.news_controller.model.loaders;

import com.example.administrator.news_controller.model.entities.News;

import io.reactivex.Single;

public interface INewsLoader {
    Single<News> getNewsSingle();
}
