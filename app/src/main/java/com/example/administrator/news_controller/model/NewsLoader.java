package com.example.administrator.news_controller.model;

import android.content.Context;
import android.util.Log;

import com.example.administrator.news_controller.News;
import com.example.administrator.news_controller.NewsItem;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public class NewsLoader implements INewsLoader {

    private Context context;

    public static final String LOG_TAG = NewsLoader.class.getName();

    public NewsLoader(Context context) {
        this.context = context;
    }

    public interface NewsService{

        @GET("rss/all")
        Call<News> fetchNewsItems();
    }

    public interface NewsListener{
        void onLoaded(List<NewsItem> news);
    }

    public void loadNews(final NewsLoader.NewsListener listener){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meduza.io/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        NewsLoader.NewsService newsService = retrofit.create(NewsLoader.NewsService.class);
        Call<News> call = newsService.fetchNewsItems();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, final Response<News> response) {
                if (response.isSuccessful()){
                    final List<NewsItem> newsItems = response.body().getChannel().getNewsItems();
                    listener.onLoaded(newsItems);
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
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
            }
        });
    }
}
