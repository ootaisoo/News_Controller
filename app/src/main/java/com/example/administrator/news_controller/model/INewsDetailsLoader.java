package com.example.administrator.news_controller.model;

import com.example.administrator.news_controller.DetailedNews;

import io.reactivex.Single;

public interface INewsDetailsLoader {
    Single<DetailedNews> getDetailsSingle(String newsPath);
}
