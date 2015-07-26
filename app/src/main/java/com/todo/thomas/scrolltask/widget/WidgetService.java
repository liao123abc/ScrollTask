package com.todo.thomas.scrolltask.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by Administrator on 2015/7/7.
 */
public class WidgetService extends RemoteViewsService{
    private final static String TAG = "WidgetService";

    public WidgetService() {
        Log.v(TAG, "WidgetService start up");
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        //Log.v(TAG, "appWidgetId is : " + appWidgetId);
        return (new RemoteListViewFactory(this.getApplicationContext(), intent));
    }



}
