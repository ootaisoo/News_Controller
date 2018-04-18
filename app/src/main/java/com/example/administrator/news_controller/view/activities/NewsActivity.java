package com.example.administrator.news_controller.view.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.news_controller.R;
import com.example.administrator.news_controller.Root;
import com.example.administrator.news_controller.UrlUtils;
import com.example.administrator.news_controller.model.NewsDetailsFromDBLoader;
import com.example.administrator.news_controller.model.NewsDetailsLoader;
import com.example.administrator.news_controller.presenter.NewsPresenter;
import com.example.administrator.news_controller.view.NewsView;

public class NewsActivity extends BaseActivity<NewsPresenter> implements NewsView {

    public static final String LOG_TAG = NewsActivity.class.getName();

    @Override
    protected void inject() {
        setPresenter(new NewsPresenter(this,
                new NewsDetailsLoader(),
                new NewsDetailsFromDBLoader()));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        Intent intent = getIntent();
        String newsPath = intent.getStringExtra("newsPath");

        if(isNetworkConnected()) {
            getPresenter().loadDetails(newsPath);
        } else {
            getPresenter().loadDetailsFromDb(newsPath);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void addDetailedNewsItem(Root root) {
        TextView titleTextView = findViewById(R.id.news_title);
        titleTextView.setText(root.getTitle());
        TextView newsBodyTextView = findViewById(R.id.news_body);
        newsBodyTextView.setText(Html.fromHtml(root.getContent().getBody()));
        ImageView newsImageView = findViewById(R.id.news_image);
        UrlUtils.setImageFromUrl(this, newsImageView, "http://meduza.io/" + root.getShareImage());
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
