package com.example.administrator.news_controller.model;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;


import com.example.administrator.news_controller.News;
import com.example.administrator.news_controller.NewsItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class NewsFromDbLoader implements INewsFromDbLoader {

    Context context;

    public static final String LOG_TAG = NewsFromDbLoader.class.getName();

    public NewsFromDbLoader(Context context){
        this.context = context;
    }

    public interface NewsListener{
        void onLoaded(List<NewsItem> news);
    }

    public void loadNewsFromDb(NewsListener listener){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewsItem> result = realm.where(NewsItem.class).findAllAsync();
        if (!result.isEmpty()) {
            List<NewsItem> newsItems = result.subList(0, result.size() - 1);
            listener.onLoaded(newsItems);
        }
    }
}
