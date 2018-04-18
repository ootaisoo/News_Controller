package com.example.administrator.news_controller.model.loaders;

import com.example.administrator.news_controller.model.entities.Root;

import io.realm.Realm;
import io.realm.RealmResults;

public class NewsDetailsFromDBLoader implements INewsDetailsFromDBLoader {

    public static final String LOG_TAG = NewsDetailsFromDBLoader.class.getName();

    public interface DetailsFromDbListener{
        void onLoaded(Root root);
    }

    public void loadDetailsFromDb(String newsPath, DetailsFromDbListener listener){
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

    public void saveDetailsToDb(final Root root){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(root);
            }
        });
        realm.close();
    }
}
