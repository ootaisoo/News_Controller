package com.example.administrator.news_controller.model;

import com.example.administrator.news_controller.DetailedNews;
import com.example.administrator.news_controller.Root;

import io.reactivex.Single;

public interface INewsDetailsLoader {
    Single<DetailedNews> getDetailsSingle(String newsPath);
}
