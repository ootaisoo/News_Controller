package com.example.administrator.news_controller.view;

import com.example.administrator.news_controller.NewsItem;

import java.util.List;

public interface MainView extends MvpView {
    void onNewsLoaded(List<NewsItem> news);
}
