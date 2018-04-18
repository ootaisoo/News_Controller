package com.example.administrator.news_controller.model.loaders;

import com.example.administrator.news_controller.model.entities.News;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public class NewsLoader implements INewsLoader {

    public static final String LOG_TAG = NewsLoader.class.getName();

    private final NewsService newsService;

    public NewsLoader() {
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