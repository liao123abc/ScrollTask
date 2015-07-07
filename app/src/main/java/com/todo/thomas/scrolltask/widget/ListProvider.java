package com.todo.thomas.scrolltask.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;


import com.todo.thomas.scrolltask.R;

/**
 * Created by Administrator on 2015/7/5.
 */

//TODO: https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
public class ListProvider implements RemoteViewsService.RemoteViewsFactory{
    private ArrayList listItemList = new ArrayList();
    private Context context;
    private int appWidgetId;

    public static class ListItem {
        public String heading;
        public String content;

        ListItem(String heading, String content) {
            this.heading = heading;
            this.content = content;
        }

        public ListItem() {

        }
    }

    String[] testValues = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
            "Android", "iPhone", "WindowsMobile" };
    private int testCount = 7;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {
        for (int i = 0; i < testCount; i++) {
            ListItem listItem = new ListItem();
            listItem.heading = "Heading" + i;
            listItem.content = testValues[i];
            listItemList.add(listItem);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.appwidget_list_item);
        ListItem listItem = (ListItem)listItemList.get(position);
        remoteView.setTextViewText(R.id.title, listItem.heading);
        remoteView.setTextViewText(R.id.detail, listItem.content);

        return remoteView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}