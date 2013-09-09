package com.appstudio.android.sample.ch_29_1;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.appstudio.android.sample.R;

public class StackWidgetProvider extends AppWidgetProvider {
  public static final String TOAST_ACTION = 
                 "com.appstudio.sample.android.TOAST_ACTION";
  public static final String EXTRA_ITEM = 
                   "com.appstudio.sample.android.EXTRA_ITEM";

  @Override
  public void onDeleted(Context context,int[] appWidgetIds) {
    super.onDeleted(context, appWidgetIds);
  }

  @Override
  public void onDisabled(Context context) {
    super.onDisabled(context);
  }

  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals(TOAST_ACTION)) {
      int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
      Toast.makeText(context,viewIndex+"번째 뷰를 터치하였습니다.",
                                  Toast.LENGTH_SHORT).show();
    }
    super.onReceive(context, intent);
  }
                                                       
  @Override
  public void onUpdate(Context context, 
     AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    // 원격 어댑터로 위젯을 업데이트한다.
    for (int i = 0; i < appWidgetIds.length; ++i) {
      // 인텐트가 StackViewService를 가리키도록 한다.
      // 이 서비스는 뷰에 컬렉션 데이터를 제공한다. 
      Intent intent = new Intent(context, 
                                   StackWidgetService.class);
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 
                                            appWidgetIds[i]);
      // 인텐트가 비교될때 엑스트라는 무시된다 무시되지 않도록 하기 위해 
      // 엑스트라를 데이터에 집어넣는다.
      intent.setData(Uri.parse(intent.toUri
                                (Intent.URI_INTENT_SCHEME)));
      RemoteViews rv = new RemoteViews(
                                    context.getPackageName(),
                             R.layout.stack_widget_provider);
      rv.setRemoteAdapter(appWidgetIds[i], 
                                    R.id.stack_view, intent);

      // 컬렉션이 아이템을 가지지 않는 경우 공백 뷰를 출력한다. 
      // 공백 뷰는 컬렉션 뷰의 형제 뷰여야 한다.
      rv.setEmptyView(R.id.stack_view, R.id.empty_view);

      // 펜딩 인텐트 템플릿을 설정한다. 컬렉션의 개별 아이템을 위한 펜딩
      // 인텐트는 비용이 많이 들어 불가하다. 대신에 전체 컬렉션에 대한 
      // 펜딩 인텐트 템플릿을준비하고 개별 아이템 구분을 위해서는
      // setOnClickFillInIntent()를 사용한다.
      Intent toastIntent = new Intent(context, 
                                  StackWidgetProvider.class);
      toastIntent.setAction(
                           StackWidgetProvider.TOAST_ACTION);
      toastIntent.putExtra(
       AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
      intent.setData(Uri.parse(intent.toUri(
                                 Intent.URI_INTENT_SCHEME)));
      PendingIntent toastPendingIntent = 
          PendingIntent.getBroadcast(context, 0, toastIntent,
                          PendingIntent.FLAG_UPDATE_CURRENT);
      rv.setPendingIntentTemplate(R.id.stack_view, 
                                         toastPendingIntent);
      appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
    }
    super.onUpdate(context, appWidgetManager, appWidgetIds);
  }
}