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
    private final NewsLoader.NewsService newsService;

    public NewsLoader(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meduza.io/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        newsService = retrofit.create(NewsLoader.NewsService.class);
    }

    public NewsService getNewsService() {
        return newsService;
    }

    public interface NewsService{

        @GET("rss/all")
        Single<News> fetchNewsItems();
    }

    public void saveToDb(News news) {
        final List<NewsItem> newsItems = news.getChannel().getNewsItems();
        Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            for (NewsItem newsItem : newsItems){
                                realm.copyToRealmOrUpdate(newsItem);
                                Log.e(LOG_TAG, newsItem.getUrl());
                            }
                        }
                    });
                    realm.close();
    }

//        call.enqueue(new Callback<News>() {
//            @Override
//            public void onResponse(Call<News> call, final Response<News> response) {
//                if (response.isSuccessful()){
//                    final List<NewsItem> newsItems = response.body().getChannel().getNewsItems();
//                    listener.onLoaded(newsItems);
//                    Realm realm = Realm.getDefaultInstance();
//                    realm.executeTransactionAsync(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            for (NewsItem newsItem : newsItems){
//                                realm.copyToRealmOrUpdate(newsItem);
//                                Log.e(LOG_TAG, newsItem.getUrl());
//                            }
//                        }
//                    });
//                    realm.close();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<News> call, Throwable t) {
//                Log.e(LOG_TAG, t.toString());
//            }
//        });
//    }
}
