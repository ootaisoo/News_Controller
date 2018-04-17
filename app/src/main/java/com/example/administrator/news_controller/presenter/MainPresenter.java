package com.example.administrator.news_controller.presenter;

import android.util.Log;

import com.example.administrator.news_controller.News;
import com.example.administrator.news_controller.NewsItem;
import com.example.administrator.news_controller.model.INewsFromDbLoader;
import com.example.administrator.news_controller.model.INewsLoader;
import com.example.administrator.news_controller.model.NewsFromDbLoader;
import com.example.administrator.news_controller.model.NewsLoader;
import com.example.administrator.news_controller.view.MainView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MainPresenter extends BasePresenter<MainView> {

    public static final String LOG_TAG = MainPresenter.class.getName();

    private INewsLoader newsLoader;
    private INewsFromDbLoader newsFromDbLoader;
    private DisposableSingleObserver<News> disposableSingleObserver;

    public MainPresenter(MainView view,
                         INewsLoader newsLoader,
                         INewsFromDbLoader newsFromDbLoader) {
        super(view);
        this.newsLoader = newsLoader;
        this.newsFromDbLoader = newsFromDbLoader;

        disposableSingleObserver = new DisposableSingleObserver<News>() {
            @Override
            public void onSuccess(News news) {
                List<NewsItem> newsItems = news.getChannel().getNewsItems();
                getView().onNewsLoaded(newsItems);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, e.toString());
            }
        };
    }

    public void loadNews(){
        Single<News> single = newsLoader.getNewsService().fetchNewsItems();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(new Consumer<News>() {
                    @Override
                    public void accept(News news) throws Exception {
                        newsLoader.saveToDb(news);
                    }
                })
                .subscribe(disposableSingleObserver);
    }

    public void loadNewsFromDb(){
        newsFromDbLoader.loadNewsFromDb(newsFromDbListener);
    }

    private NewsFromDbLoader.NewsListener newsFromDbListener = new NewsFromDbLoader.NewsListener() {
        @Override
        public void onLoaded(List<NewsItem> news) {
            getView().onNewsLoaded(news);
        }
    };
}
