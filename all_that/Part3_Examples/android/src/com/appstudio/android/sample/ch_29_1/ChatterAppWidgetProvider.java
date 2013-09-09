/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appstudio.android.sample.ch_29_1;

import java.util.Random;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.appstudio.android.sample.R;


public class ChatterAppWidgetProvider 
                                  extends AppWidgetProvider {
  private static final String TAG = "appstudio";

  @Override
  public void onUpdate(Context context, 
    AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    Log.d(TAG, "onUpdate");
    // 홈화면에 추가된 모든 앱 위젯에 대해 업데이트를 수행한다.
    // 이때 위젯 추가시 설정 액티비티에 의해 저장된 정보를 이용한다.
    final int N = appWidgetIds.length;
    for (int i=0; i<N; i++) {
      int appWidgetId = appWidgetIds[i];
      String titlePrefix = ChatterAppWidgetConfigure.
                         loadTitlePref(context, appWidgetId);
      updateAppWidget(context, appWidgetManager, 
                                   appWidgetId, titlePrefix);
    }
  }
    
  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    Log.d(TAG, "onDeleted");
    // 홈화면에서 앱 위젯이 삭제될 때 수행된다.
    // 프레퍼런스를 정리한다. 현재는 프레퍼런스 삭제는 구현 안되어 있다.
    final int N = appWidgetIds.length;
    for (int i=0; i<N; i++) {
      ChatterAppWidgetConfigure.deleteTitlePref(context, 
                                            appWidgetIds[i]);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // 최초로 위젯을 추가할 때나 , 부팅타임시 수행
    Log.d(TAG, "onEnabled");
  }

  @Override
  public void onDisabled(Context context) {
    Log.d(TAG, "onDisabled");
  }

  static void updateAppWidget(Context context, 
                           AppWidgetManager appWidgetManager,
                       int appWidgetId, String titlePrefix) {
    String[] chatters = context.getResources().
                             getStringArray(R.array.chatter);
    int index = new Random().nextInt(chatters.length);
    String chatter = chatters[index];
    String message = ChatterAppWidgetConfigure.
          loadTitlePref(context, appWidgetId) + "\n"+chatter;

    // 리모트뷰를 생성 후 원하는 값을 설정한다.
    RemoteViews views = new RemoteViews(
      context.getPackageName(), R.layout.appwidget_provider);
    views.setTextViewText(R.id.appwidget_text, message);

    // 위젯 매니저에게 리모트뷰 정보에 기초하여 위젯을 수정할 것을 요청
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }
}


