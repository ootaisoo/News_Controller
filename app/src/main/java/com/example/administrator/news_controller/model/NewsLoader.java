package com.example.administrator.news_controller.model;

import android.content.Context;
import android.util.Log;

import com.example.administrator.news_controller.News;
import com.example.administrator.news_controller.NewsItem;

import java.util.List;

import io.reactivex.Single;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public class NewsLoader implements INewsLoader {

    public static final String LOG_TAG = NewsLoader.class.getName();

    private Context context;
    private final NewsService newsService;

    public NewsLoader(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meduza.io/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        newsService = retrofit.create(NewsService.class);
    }

    public Single<News> getNewsSingle() {
        return newsService.fetchNewsItems();
    }

    public interface NewsService {

        @GET("rss/all")
        Single<News> fetchNewsItems();
    }
}