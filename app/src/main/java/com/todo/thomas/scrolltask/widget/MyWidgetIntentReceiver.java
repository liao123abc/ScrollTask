package com.todo.thomas.scrolltask.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.todo.thomas.scrolltask.R;

/**
 * Created by Thomas Liao on 2015/6/30.
 */
public class MyWidgetIntentReceiver extends BroadcastReceiver{

    public static int clickCount = 0;
    private String msg[] = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WidgetUtils.WIDGET_UPDATE_ACTION)) {
            updateWidgetPictureAndButtonListener(context);
        }

    }

    private void updateWidgetPictureAndButtonListener(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_layout);

        //updating view
//        remoteViews.setTextViewText(R.id.title, getTitle());
//        remoteViews.setTextViewText(R.id.desc, getDesc(context));

//        //re-registering for click listener
//        remoteViews.setOnClickPendingIntent(R.id.sync_button,
//                MyWidgetProvider.buildButtonPendingIntent(context));
//
//        MyWidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
    }

    private String getDesc(Context context) {
        msg = context.getResources().getStringArray(R.array.widget_string_array);
        if (clickCount >= msg.length) {
            clickCount = 0;
        }
        return msg[clickCount];
    }

    private String getTitle() {
        return "Funny Jokes";
    }

}
