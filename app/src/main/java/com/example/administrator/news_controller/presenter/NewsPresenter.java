package com.example.administrator.news_controller.presenter;

import android.util.Log;

import com.example.administrator.news_controller.DetailedNews;
import com.example.administrator.news_controller.Root;
import com.example.administrator.news_controller.model.INewsDetailsFromDBLoader;
import com.example.administrator.news_controller.model.INewsDetailsLoader;
import com.example.administrator.news_controller.model.NewsDetailsFromDBLoader;
import com.example.administrator.news_controller.model.NewsDetailsLoader;
import com.example.administrator.news_controller.view.NewsView;

public class NewsPresenter extends BasePresenter<NewsView> {

    public static final String LOG_TAG = NewsPresenter.class.getName();

    private INewsDetailsLoader newsDetailsLoader;
    private INewsDetailsFromDBLoader newsDetailsFromDBLoader;

    public NewsPresenter(NewsView view,
                         INewsDetailsLoader newsDetailsLoader,
                         INewsDetailsFromDBLoader newsDetailsFromDBLoader) {
        super(view);
        this.newsDetailsLoader = newsDetailsLoader;
        this.newsDetailsFromDBLoader = newsDetailsFromDBLoader;
    }

    public void loadDetails(String newsPath){
        newsDetailsLoader.loadDetails(newsPath, detailsListener);
    }

    public void loadDetailsFromDb(String newsPath){
        newsDetailsFromDBLoader.loadNewsDetailsFromDb(newsPath, detailsFromDbListener);
    }

    private NewsDetailsLoader.DetailsListener detailsListener = new NewsDetailsLoader.DetailsListener() {
        @Override
        public void onLoaded(DetailedNews detailedNews) {
            getView().addDetailedNewsItem(detailedNews.getRoot());
        }
    };

    private NewsDetailsFromDBLoader.DetailsFromDbListener detailsFromDbListener = new NewsDetailsFromDBLoader.DetailsFromDbListener() {
        @Override
        public void onLoaded(Root detailedNews) {
            getView().addDetailedNewsItem(detailedNews);
        }
    };
}
