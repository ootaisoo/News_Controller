package com.example.administrator.news_controller;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import io.realm.RealmObject;

@Root(name="rss", strict = false)
public class News extends RealmObject{

    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
