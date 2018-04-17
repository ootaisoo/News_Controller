package com.example.administrator.news_controller.presenter;

import com.example.administrator.news_controller.News;
import com.example.administrator.news_controller.NewsItem;
import com.example.administrator.news_controller.model.INewsFromDbLoader;
import com.example.administrator.news_controller.model.INewsLoader;
import com.example.administrator.news_controller.model.NewsFromDbLoader;
import com.example.administrator.news_controller.model.NewsLoader;
import com.example.administrator.news_controller.view.MainView;

import java.util.List;

public class MainPresenter extends BasePresenter<MainView> {

    public static final String LOG_TAG = MainPresenter.class.getName();

    private INewsLoader newsLoader;
    private INewsFromDbLoader newsFromDbLoader;

    public MainPresenter(MainView view,
                         INewsLoader newsLoader,
                         INewsFromDbLoader newsFromDbLoader) {
        super(view);
        this.newsLoader = newsLoader;
        this.newsFromDbLoader = newsFromDbLoader;
    }

    public void loadNews(){
        newsLoader.loadNews(newsListener);
    }

    public void loadNewsFromDb(){
        newsFromDbLoader.loadNewsFromDb(newsFromDbListener);
    }

    private NewsLoader.NewsListener newsListener = new NewsLoader.NewsListener() {
        @Override
        public void onLoaded(List<NewsItem> news) {
            getView().onNewsLoaded(news);
        }
    };

    private NewsFromDbLoader.NewsListener newsFromDbListener = new NewsFromDbLoader.NewsListener() {
        @Override
        public void onLoaded(List<NewsItem> news) {
            getView().onNewsLoaded(news);
        }
    };
}
