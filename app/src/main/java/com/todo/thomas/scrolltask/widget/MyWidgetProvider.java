package com.todo.thomas.scrolltask.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.todo.thomas.scrolltask.R;

/**
 * Created by Thomas on 2015/6/28.
 */

//TODO http://javatechig.com/android/app-widgets-example-in-android

public class MyWidgetProvider extends AppWidgetProvider{
    private static final String TAG = "MyWidgetProvider";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // initializing widget layout
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        // register for button event
        remoteViews.setOnClickPendingIntent(R.id.sync_button, buildButtonPendingIntent(context));
        // updating view with initial data
        remoteViews.setTextViewText(R.id.title, getTitle());
        Log.d(TAG, "set title " + getTitle());
        remoteViews.setTextViewText(R.id.desc, getDesc());
        Log.d(TAG, "set desc " + getDesc());

        //request for widget update
        pushWidgetUpdate(context, remoteViews);

    }

    public static PendingIntent buildButtonPendingIntent(Context context) {
        ++MyWidgetIntentReceiver.clickCount;

        Log.d(TAG, "buildButtonPendingIntent");
        //initiate widget update request
        Intent intent = new Intent();
        intent.setAction(WidgetUtils.WIDGET_UPDATE_ACTION);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static CharSequence getDesc() {
        return "Sync to see some of our funniest joke collections";
    }

    private static CharSequence getTitle() {
        return "Funny Jokes";
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context, MyWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }

}
