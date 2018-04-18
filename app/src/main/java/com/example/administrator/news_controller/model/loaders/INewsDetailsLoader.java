package com.example.administrator.news_controller.model.loaders;

import com.example.administrator.news_controller.model.entities.DetailedNews;

import io.reactivex.Single;

public interface INewsDetailsLoader {
    Single<DetailedNews> getDetailsSingle(String newsPath);
}
