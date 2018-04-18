package com.example.administrator.news_controller;

import android.content.Context;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtils {

    public static void setImageFromUrl(Context context, ImageView imageView, String url) {
        if (url != null) {
            Glide
                    .with(context)
                    .load(url)
                    .asBitmap()
                    .into(imageView);
        }
    }

    public static URL parseUrl(String s){
        URL url = null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return  url;
    }
}