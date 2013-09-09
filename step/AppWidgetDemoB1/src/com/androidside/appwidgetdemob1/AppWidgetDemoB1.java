package com.androidside.appwidgetdemob1;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class AppWidgetDemoB1 extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1,
                1000);
    }

    private class MyTime extends TimerTask {
        RemoteViews remoteViews;
        AppWidgetManager appWidgetManager;
        ComponentName thisWidget;

        public MyTime(Context context, AppWidgetManager appWidgetManager) {
            this.appWidgetManager = appWidgetManager;
            remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.timewidget);
            thisWidget = new ComponentName(context, AppWidgetDemoB1.class);
        }

        @Override
        public void run() {
            remoteViews.setTextViewText(R.id.text, getTime());
            appWidgetManager.updateAppWidget(thisWidget, remoteViews);
        }
        
        //현재 시간/분/초를 반환하는 메소드
        private String getTime() {
            Calendar now = Calendar.getInstance();
            int h = now.get(Calendar.HOUR_OF_DAY);

            int m = now.get(Calendar.MINUTE);
            int s = now.get(Calendar.SECOND);

            return h + ":" + m + ":" + s;
        }
    }
}