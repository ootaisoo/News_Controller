package com.example.administrator.news_controller.presenter;

import android.util.Log;

import com.example.administrator.news_controller.DetailedNews;
import com.example.administrator.news_controller.Root;
import com.example.administrator.news_controller.model.INewsDetailsFromDBLoader;
import com.example.administrator.news_controller.model.INewsDetailsLoader;
import com.example.administrator.news_controller.model.NewsDetailsFromDBLoader;
import com.example.administrator.news_controller.view.NewsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsPresenter extends BasePresenter<NewsView> {

    public static final String LOG_TAG = NewsPresenter.class.getName();

    private INewsDetailsLoader newsDetailsLoader;
    private INewsDetailsFromDBLoader newsDetailsFromDBLoader;
    private DisposableSingleObserver<DetailedNews> disposableSingleObserver;

    public NewsPresenter(NewsView view,
                         INewsDetailsLoader newsDetailsLoader,
                         INewsDetailsFromDBLoader newsDetailsFromDBLoader) {
        super(view);
        this.newsDetailsLoader = newsDetailsLoader;
        this.newsDetailsFromDBLoader = newsDetailsFromDBLoader;
        disposableSingleObserver = new DisposableSingleObserver<DetailedNews>() {
            @Override
            public void onSuccess(DetailedNews detailedNews) {
                getView().addDetailedNewsItem(detailedNews.getRoot());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, e.toString());
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        disposableSingleObserver.dispose();
    }

    public void loadDetails(String newsPath){
        newsDetailsLoader.getDetailsSingle(newsPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(new Consumer<DetailedNews>() {
                    @Override
                    public void accept(DetailedNews detailedNews) throws Exception {
                        newsDetailsFromDBLoader.saveDetailsToDb(detailedNews.getRoot());
                    }
                })
                .subscribe(disposableSingleObserver);
    }

    public void loadDetailsFromDb(String newsPath){
        newsDetailsFromDBLoader.loadDetailsFromDb(newsPath, detailsFromDbListener);
    }

    private NewsDetailsFromDBLoader.DetailsFromDbListener detailsFromDbListener = new NewsDetailsFromDBLoader.DetailsFromDbListener() {
        @Override
        public void onLoaded(Root detailedNews) {
            getView().addDetailedNewsItem(detailedNews);
        }
    };
}
