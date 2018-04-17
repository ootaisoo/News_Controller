package com.example.administrator.news_controller.model;

import android.content.Context;
import android.util.Log;

import com.example.administrator.news_controller.DetailedNews;
import com.example.administrator.news_controller.Root;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Single;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class NewsDetailsLoader implements INewsDetailsLoader {

    private Context context;
    private final DetailsService detailsService;

    public static final String LOG_TAG = NewsDetailsLoader.class.getName();

    public NewsDetailsLoader(Context context) {
        this.context = context;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meduza.io/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        detailsService = retrofit.create(DetailsService.class);
    }

    public Single<DetailedNews> getDetailsSingle(String newsPath) {
        return detailsService.fetchDetails(newsPath);
    }

    public interface DetailsService {

        @GET("/api/v3/{path}")
        Single<DetailedNews> fetchDetails(@Path("path") String newsPath);
    }
}
