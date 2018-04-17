package com.example.administrator.news_controller;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Root(name="rss", strict = false)
public class News extends RealmObject{

    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
