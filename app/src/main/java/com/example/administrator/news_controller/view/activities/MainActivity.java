package com.example.administrator.news_controller.view.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.administrator.news_controller.NewsItem;
import com.example.administrator.news_controller.R;
import com.example.administrator.news_controller.model.NewsFromDbLoader;
import com.example.administrator.news_controller.model.NewsLoader;
import com.example.administrator.news_controller.presenter.MainPresenter;
import com.example.administrator.news_controller.view.MainView;
import com.example.administrator.news_controller.view.adapters.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView, NewsAdapter.NewsSelectedListener {

    public static final String LOG_TAG = MainActivity.class.getName();

    private NewsAdapter newsAdapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void inject() {
        setPresenter(new MainPresenter(this,
                new NewsLoader(),
                new NewsFromDbLoader()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView newsRecyclerView = findViewById(R.id.main_recycler);
        progressBar = findViewById(R.id.progress_bar);
        swipeContainer = findViewById(R.id.swipe_container);

        swipeContainer = findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isNetworkConnected()) {
                    getPresenter().loadNews();
                } else {
                    getPresenter().loadNewsFromDb();
                }
                swipeContainer.setRefreshing(false);
            }
        });

        final List<NewsItem> news = new ArrayList<>();
        newsAdapter = new NewsAdapter(news, this, this);

        newsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager newslayoutManager = new LinearLayoutManager(this);
        newsRecyclerView.setLayoutManager(newslayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, newslayoutManager.getOrientation());
        newsRecyclerView.addItemDecoration(dividerItemDecoration);

        newsRecyclerView.setAdapter(newsAdapter);

        progressBar.setVisibility(View.GONE);
        if(isNetworkConnected()) {
            getPresenter().loadNews();
        } else {
            getPresenter().loadNewsFromDb();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void onNewsLoaded(List<NewsItem> news){
        progressBar.setVisibility(View.GONE);
        newsAdapter.clear();
        newsAdapter.addAll(news);
    }

    @Override
    public void onNewsSelected(String newsPath) {
        Intent newsActivityIntent = new Intent(this, NewsActivity.class);
        newsActivityIntent.putExtra("newsPath", newsPath);
        startActivity(newsActivityIntent);
    }
}
