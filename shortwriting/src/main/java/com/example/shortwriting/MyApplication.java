package com.example.shortwriting;

import android.app.Application;

import com.thomasliao.persistence.Persistence;

/**
 * Created by thomasliao on 2017/12/5.
 */

public class MyApplication extends Application {

    private Persistence persistence;

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    private void initGreenDao() {
        persistence = new Persistence(this);
    }
}
