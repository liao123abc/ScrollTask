package com.todo.thomas.scrolltask.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.todo.thomas.scrolltask.R;

/**
 * Created by Thomas on 2015/6/28.
 * If you have a background service, you can push widget updates on your own schedule,
 * or the AppWidget framework provides an automatic update mechanism.
 * 1.BroadcastReceivers are subject to the Application Not Responding (ANR) timer,
 * which may prompt users to force close our app if it's taking too long
 * 2.Making a web request might take several seconds, so we may use the service to avoid any ANR timeouts.
 * 3.if we have a service in the background, we can update the widget by service,
 * we can also send data to service from widget
 *
 * TODO: add a service to support service and widget interaction later
 * http://rxwen.blogspot.com/2012/10/communication-between-android-widget.html
 */

/**
 * tips: app widget does not have to get a service to serve
 *
 */

//TODO http://javatechig.com/android/app-widgets-example-in-android

public class MyWidgetProvider extends AppWidgetProvider{
    private static final String TAG = "MyWidgetProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        /*int[] appWidgetIds holds ids of multiple instance
         * of your widget
         * meaning you are placing more than one widgets on
         * your homescreen
         * */
        final int N = appWidgetIds.length;
        for(int i = 0; i < N; i++) {
            RemoteViews remoteViews = updateWidgetListView(context, appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent svcIntent = new Intent(context, WidgetService.class);
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        remoteViews.setRemoteAdapter(appWidgetId, R.id.listView, svcIntent);
        return remoteViews;
    }

    public static PendingIntent buildButtonPendingIntent(Context context) {
        Log.d(TAG, "buildButtonPendingIntent");
        //initiate widget update request
        Intent intent = new Intent();
        intent.setAction(WidgetUtils.WIDGET_UPDATE_ACTION);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    /*
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int N = appWidgetIds.length;
        for(int i = 0; i < N; i++) {
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
    */

}
