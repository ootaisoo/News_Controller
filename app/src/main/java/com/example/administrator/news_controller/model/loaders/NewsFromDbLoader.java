package com.example.administrator.news_controller.model.loaders;

import com.example.administrator.news_controller.model.entities.NewsItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class NewsFromDbLoader implements INewsFromDbLoader {

    public static final String LOG_TAG = NewsFromDbLoader.class.getName();

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
        realm.close();
    }

    public void saveNewsToDb(final List<NewsItem> news) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (NewsItem newsItem : news) {
                    realm.copyToRealmOrUpdate(newsItem);
                }
            }
        });
        realm.close();
    }
}
