package com.example.administrator.news_controller;

import org.simpleframework.xml.*;
import org.simpleframework.xml.Root;

import java.util.List;

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
