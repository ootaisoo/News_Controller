package com.example.administrator.news_controller.view;

import com.example.administrator.news_controller.model.entities.Root;

public interface NewsView extends MvpView {
    void addDetailedNewsItem(Root detailedNews);
}
