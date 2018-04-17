package com.example.administrator.news_controller.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.news_controller.view.MvpView;

public abstract class BasePresenter<V extends MvpView> {

    private final V view;

    BasePresenter(V view){
        this.view = view;
    }

    public V getView() {
        return view;
    }

    public void onCreate(@Nullable Bundle savedInstanceState){}
    public void onStart(){}
    public void onResume(){}
    public void onPause(){}
    public void onStop(){}
    public void onDestroy(){}
}
