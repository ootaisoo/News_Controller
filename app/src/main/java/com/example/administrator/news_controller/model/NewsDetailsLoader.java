package com.example.administrator.news_controller.model;

import android.content.Context;
import android.util.Log;

import com.example.administrator.news_controller.DetailedNews;
import com.example.administrator.news_controller.Root;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class NewsDetailsLoader implements INewsDetailsLoader {

    private Context context;
    private DetailsService detailsService;

    public static final String LOG_TAG = NewsDetailsLoader.class.getName();

    public NewsDetailsLoader(Context context) {
        this.context = context;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meduza.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        detailsService = retrofit.create(DetailsService.class);
    }

    public interface DetailsService {

        @GET("/api/v3/{path}")
        Call<DetailedNews> fetchDetails(@Path("path") String newsPath);
    }

    public interface DetailsListener {
        void onLoaded(DetailedNews root);
    }

    public void loadDetails(String newsPath, final NewsDetailsLoader.DetailsListener listener) {
        Log.e(LOG_TAG, newsPath);
        detailsService.fetchDetails(newsPath).enqueue(new Callback<DetailedNews>() {
            @Override
            public void onResponse(Call<DetailedNews> call, final Response<DetailedNews> response) {
                if (response.isSuccessful()) {
                    Log.e(LOG_TAG, response.body().getRoot().getUrl());
                    listener.onLoaded(response.body());
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealmOrUpdate(response.body().getRoot());
                        }
                    });
                    realm.close();
                }
            }

            @Override
            public void onFailure(Call<DetailedNews> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
            }
        });
    }
}
