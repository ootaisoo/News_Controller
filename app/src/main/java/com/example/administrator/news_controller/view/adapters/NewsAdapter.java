package com.example.administrator.news_controller.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.news_controller.model.entities.NewsItem;
import com.example.administrator.news_controller.R;
import com.example.administrator.news_controller.UrlUtils;

import java.net.URL;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public static final String LOG_TAG = NewsAdapter.class.getName();

    private List<NewsItem> newsItems;
    private NewsSelectedListener listener;
    private Context context;

    public NewsAdapter(List<NewsItem> newsItems, Context context, NewsSelectedListener listener) {
        this.listener = listener;
        this.newsItems = newsItems;
        this.context = context;
    }

    public interface NewsSelectedListener {
        void onNewsSelected(String newsPath);
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public void addAll(List<NewsItem> list) {
        newsItems.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        newsItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsItem newsItem = newsItems.get(position);

        URL newsUrl = UrlUtils.parseUrl(newsItem.getUrl());
        String newsPath = newsUrl.getPath().substring(1);

        holder.title.setText(newsItem.getTitle());
        holder.newsPath = newsPath;
        holder.imageURL = newsItem.getImageUrl();
        if (newsItem.getImageUrl() != null) {
            UrlUtils.setImageFromUrl(context, holder.image, newsItem.getImageUrl());
        } else {
            holder.image.setVisibility(View.GONE);
        }
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_holder, parent, false);
        return new NewsViewHolder(view);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        String newsPath;
        private ImageView image;
        private TextView title;
        String imageURL;

        public NewsViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                listener.onNewsSelected(newsPath);
                }
            });
        }
    }
}