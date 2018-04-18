package com.example.administrator.news_controller.model.loaders;

import com.example.administrator.news_controller.model.entities.Root;

public interface INewsDetailsFromDBLoader {
    void loadDetailsFromDb(String newsPath, NewsDetailsFromDBLoader.DetailsFromDbListener listener);
    void saveDetailsToDb(Root root);
}
