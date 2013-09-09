package com.appstudio.android.sample.ch_29_1;

import java.util.ArrayList;
import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.appstudio.android.sample.R;

public class StackWidgetService extends RemoteViewsService {
 
  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new StackRemoteViewsFactory(
                       this.getApplicationContext(), intent);
  }
}                                                            

class StackRemoteViewsFactory implements              
                      RemoteViewsService.RemoteViewsFactory {
  private static final int mCount = 10;
  private List<WidgetItem> mWidgetItems = 
                                 new ArrayList<WidgetItem>();
  private Context mContext;
  private int mAppWidgetId;

  public StackRemoteViewsFactory(Context context, 
                                             Intent intent) {
    mContext = context;
    mAppWidgetId = intent.getIntExtra(
                         AppWidgetManager.EXTRA_APPWIDGET_ID,
                      AppWidgetManager.INVALID_APPWIDGET_ID);
  }
  
  @Override
  public void onCreate() {
    // 데이터 소스로 컨넥셔을 맺거나 커서를 준비하는 등의 준비작업을 수행
    // 다운로딩같이 무거운 작업의 경우에는 onDataSetChanged()나 
    // getViewAt()로 연기한다. 20초 이상 걸리면 ANR이 발생한다.
    for (int i = 0; i < mCount; i++) {
      mWidgetItems.add(new WidgetItem(i + "!"));
    }

    // 3초간 슬립해서 공백 뷰를 보여준다. 
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onDestroy() {
    // 커서, 커넥션등에 대해 정리해야 한다.
    mWidgetItems.clear();
  }

  @Override
  public int getCount() {
    return mCount;
  }

  @Override
  public RemoteViews getViewAt(int position) {
    // position은 0~count()-1 이다.
    // 아이템의 레이아웃과 position에 기반해 데이터를 추출하여
    // 호스트의 원격 뷰를 위한 RemoteViews객체를 구성한다.
    RemoteViews rv=new RemoteViews(mContext.getPackageName(),
                        R.layout.stack_widget_provider_item);
    rv.setTextViewText(R.id.widget_item, 
                            mWidgetItems.get(position).text);

    Bundle extras = new Bundle();
    extras.putInt(StackWidgetProvider.EXTRA_ITEM, position);
    Intent fillInIntent = new Intent();
    fillInIntent.putExtras(extras);
    rv.setOnClickFillInIntent(R.id.widget_item,fillInIntent);

    // 시간이 오래 걸리는 작업을 이곳에서 수행할 수도 있다.
    // 수행시간동안 해당 위치에 로딩뷰가 뜰것이다.
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return rv;
  }

  @Override
  public RemoteViews getLoadingView() {
    // 커스텀 로딩뷰를 제공한다. 
    //  널로 하면 디폴트 로딩뷰를 사용한다.
    return null;
    }

  @Override
  public int getViewTypeCount() {
    return 1;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }
  
  @Override
  public boolean hasStableIds() {
    return true;
  }

  @Override
  public void onDataSetChanged() {
  }
}