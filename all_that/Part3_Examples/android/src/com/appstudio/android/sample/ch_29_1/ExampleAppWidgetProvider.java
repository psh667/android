package com.appstudio.android.sample.ch_29_1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.appstudio.android.sample.Part03ListActivity;
import com.appstudio.android.sample.R;

public class ExampleAppWidgetProvider 
                                    extends AppWidgetProvider {
  public void onUpdate(Context context, 
       AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    final int N = appWidgetIds.length;        
    for (int i=0; i<N; i++) {            
      int appWidgetId = appWidgetIds[i];            
      Intent intent = new Intent(context, 
                                       Part03ListActivity.class);
      PendingIntent pendingIntent = 
              PendingIntent.getActivity(context, 0, intent, 0);
      RemoteViews views = new RemoteViews(
                                      context.getPackageName(),
                          R.layout.example_appwidget_provider);
      views.setOnClickPendingIntent(R.id.button,pendingIntent);
          appWidgetManager.updateAppWidget(appWidgetId, views);
    }    
  }
}