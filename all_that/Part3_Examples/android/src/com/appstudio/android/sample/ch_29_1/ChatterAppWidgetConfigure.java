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

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.appstudio.android.sample.R;



public class ChatterAppWidgetConfigure extends Activity {
  static final String TAG = "appstudio";
  private static final String PREFS_NAME = "com.appstudio." +
     "sample.android.c20_appwidget.ChatterAppWidgetProvider";

  private static final String PREF_PREFIX_KEY = "prefix_";
  int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
  EditText mAppWidgetPrefix;

  public ChatterAppWidgetConfigure() {
    super();
  }

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    // 위젯 설정 중에 사용자가 뒤로 가기를 하여 
    // 중간에 빠져나갈 버릴 경우를 대비한다.
    setResult(RESULT_CANCELED);

    setContentView(R.layout.appwidget_configure);
    mAppWidgetPrefix = (EditText)findViewById(R.id.appwidget_prefix);
    findViewById(R.id.save_button).setOnClickListener(mOnClickListener);

    // 인텐트에서 위젯 아이디를 추출한다. 
    Intent intent = getIntent();
    Bundle extras = intent.getExtras();
    if (extras != null) {
      mAppWidgetId = extras.getInt(
                         AppWidgetManager.EXTRA_APPWIDGET_ID,
                      AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    // 위젯 아이디가 없는 경우 스톱.
    if (mAppWidgetId == 
                     AppWidgetManager.INVALID_APPWIDGET_ID) {
      finish();
    }

    mAppWidgetPrefix.setText(loadTitlePref(
              ChatterAppWidgetConfigure.this, mAppWidgetId));
  }

  View.OnClickListener mOnClickListener = 
                                 new View.OnClickListener() {
    public void onClick(View v) {
      final Context context = ChatterAppWidgetConfigure.this;

      String titlePrefix = 
                       mAppWidgetPrefix.getText().toString();
      // 위젯 아이디 별로 입력한 인사말을 프레퍼런스에 저장한다.
      saveTitlePref(context, mAppWidgetId, titlePrefix);

      // 앱 위젯에 대한 업데이터를 수행한다.
      AppWidgetManager appWidgetManager = 
                       AppWidgetManager.getInstance(context);
      ChatterAppWidgetProvider.updateAppWidget(context, 
                appWidgetManager, mAppWidgetId, titlePrefix);
      // 앱 위젯 아이디를 result에 저장한다.
      Intent resultValue = new Intent();
      resultValue.putExtra(
          AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
      setResult(RESULT_OK, resultValue);
      finish();
    }
  };

  // 개별 앱 위제 인스턴스 별로 프레퍼런스에 정보를 저장한다.
  static void saveTitlePref(Context context, 
                              int appWidgetId, String text) {
    SharedPreferences.Editor prefs = 
          context.getSharedPreferences(PREFS_NAME, 0).edit();
    prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
    prefs.commit();
  }

  // 개별 앱 위젯 인스턴스 별로 프레퍼런스에 저장되어 있던 
  // 정보를 가져온다.
  static String loadTitlePref(Context context, 
                                           int appWidgetId) {
    SharedPreferences prefs = context.getSharedPreferences(
                                              PREFS_NAME, 0);
    String prefix = prefs.getString(PREF_PREFIX_KEY + 
                                          appWidgetId, null);
    if (prefix != null) {
      return prefix;
    } else {
      return "안녕하세요! ";
    }
  }

  static void deleteTitlePref(Context context, int appWidgetId) {
  }
}



