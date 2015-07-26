package com.todo.thomas.scrolltask.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.LinkedList;


import com.todo.thomas.scrolltask.R;
import com.todo.thomas.scrolltask.myprovider.Task;
import com.todo.thomas.scrolltask.myprovider.TaskCpAdapter;

/**
 * Created by Administrator on 2015/7/5.
 */

//TODO: https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
public class RemoteListViewFactory implements RemoteViewsService.RemoteViewsFactory{
    private final static String TAG = "RemoteListViewFactory";
    private ArrayList listItemList = new ArrayList();
    private Context context;
    private int appWidgetId;
    private TaskCpAdapter mCpAdapter;
    //TODO: use asyncTask to get data from database
    private LinkedList<Task> tasks = null;

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

    public RemoteListViewFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    private void populateListItem() {

        Log.d(TAG, "populating list items!");

        int count = tasks.size();

        for (int i = 0; i < count; i++) {
            ListItem listItem = new ListItem();
            Task task = tasks.get(i);
            listItem.heading = task.getName();
            listItem.content = task.getDetail();
            listItemList.add(listItem);
        }
    }

    @Override
    public void onCreate() {
        //get data from database
        mCpAdapter = new TaskCpAdapter(context);

        //get all tasks
        tasks = mCpAdapter.getAllTasks();

        populateListItem();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDataSetChanged() {
        //TODO this will be invoked when AppWidgetManager notifyAppWidgetViewDataChanged()
        /*
        1.onDataSetChanged
        2.fetch Meta Data :getCount(), getViewTypeCount(), hasStableIds(), getLoadingView()
        3.Fetch view data: getViewAt(), getItemId()
         */
        //get all tasks
        if( listItemList.size() > 0 ) {
            listItemList.clear();
        }
        tasks = mCpAdapter.getAllTasks();
        populateListItem();
    }

    @Override
    public int getViewTypeCount() {
        //listview item have different type, currently we have only one type
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
        //generate one item
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
