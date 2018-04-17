package com.example.administrator.news_controller.model;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.example.administrator.news_controller.DetailedNews;
import com.example.administrator.news_controller.News;
import com.example.administrator.news_controller.Root;

import java.lang.ref.WeakReference;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class NewsDetailsFromDBLoader implements INewsDetailsFromDBLoader {

    private Context context;

    public static final String LOG_TAG = NewsDetailsFromDBLoader.class.getName();

    public NewsDetailsFromDBLoader(Context context) {
        this.context = context;
    }

    public interface DetailsFromDbListener{
        void onLoaded(Root root);
    }

    public void loadNewsDetailsFromDb(String newsPath, DetailsFromDbListener listener){
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Root> result = realm.where(Root.class).equalTo("url", newsPath).findAllAsync();
            Root root = result.first();
            listener.onLoaded(root);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
    }
}
