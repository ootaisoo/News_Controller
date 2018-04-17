package com.example.administrator.news_controller.model;

import com.example.administrator.news_controller.Root;

public interface INewsDetailsFromDBLoader {
    void loadDetailsFromDb(String newsPath, NewsDetailsFromDBLoader.DetailsFromDbListener listener);
    void saveDetailsToDb(Root root);
}
