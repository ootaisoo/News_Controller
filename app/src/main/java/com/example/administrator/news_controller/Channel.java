package com.example.administrator.news_controller;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import io.realm.RealmList;
import io.realm.RealmObject;

@Root(name = "channel", strict = false)
public class Channel extends RealmObject{

    @ElementList(inline = true, name = "item")
    private RealmList<NewsItem> newsItems;

    public RealmList<NewsItem> getNewsItems() {
        return newsItems;
    }
}
