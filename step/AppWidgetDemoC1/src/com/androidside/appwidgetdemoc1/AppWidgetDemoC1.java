package com.androidside.appwidgetdemoc1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class AppWidgetDemoC1 extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.buttonwidget);
        
        Intent callIntent = new Intent();
        callIntent.setAction(android.content.Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:0112345678"));
        

        Uri browserUri = Uri.parse("http://m.androidside.com");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
        
        
        PendingIntent callPendingIntent = PendingIntent.getActivity(context,
                0, callIntent, 0);
        PendingIntent browserPendingIntent = PendingIntent.getActivity(context,
                0, browserIntent, 0);
        
        remoteViews.setOnClickPendingIntent(R.id.button1,
                callPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.button2,
                browserPendingIntent);
        
        
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }
}