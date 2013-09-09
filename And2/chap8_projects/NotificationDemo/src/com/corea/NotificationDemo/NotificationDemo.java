/*
Copyright (c) 2009 Hideo KINAMI

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

// HelloNotificationActivity.java
package com.corea.NotificationDemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.corea.NotificationDemo.R;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotificationDemo extends Activity {

    // NOTIFICATION_SERVICE의 매니저
    private NotificationManager mManager;
    // 마지막으로 발행한 Notification의 ID
    private int mLastId = 0;
    // 현재 등록되어있는 ID
    private List<Integer> mActiveIdList =
        new ArrayList<Integer>();

    // Notification의 게시 및 취소 버튼을 등록
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 매니저 취득
        mManager = (NotificationManager)
            getSystemService(Context.NOTIFICATION_SERVICE);

        // 버튼 등록
        setContentView(R.layout.main);
        Button b = (Button)findViewById(R.id.notify);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendNotification();
            }
        });
        b = (Button)findViewById(R.id.cancel);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelNotification();
            }
        });
    }
    
    // Notification 발생
    private void sendNotification() {
        // Notification 생성
        Notification n = new Notification();

        // 아이콘의 설정
        n.icon = R.drawable.icon;

        // tickerText의 설정
        if (hasString(R.id.tickerText))
            n.tickerText = asString(R.id.tickerText);

        // 발생 날짜 설정
        if (hasString(R.id.when))
            n.when = asDateTime(R.id.when);

        // 발생 수량 설정
        if (hasString(R.id.number))
            n.number = asInt(R.id.number);

        // 확장된 상태 표시줄 표시 설정
        n.setLatestEventInfo(
            getApplicationContext(),
            asString(R.id.contentTitle),
            asString(R.id.contentText),
            pendingIntent());

        // Notification 발생
        mManager.notify(createNotificationId(),n);
    }

    // Notification이 Tap 되었을 때에 발생되는 인텐트
    private PendingIntent pendingIntent() {
        Intent i = new Intent(
            getApplicationContext(),
            NotificationDemo.class); 
        PendingIntent pi = 
            PendingIntent.getActivity(this, 0, i, 0);
        return pi;
    }

    // 발생하는 Notification의 ID를 생성
    private int createNotificationId() {
        int id = ++mLastId;
        mActiveIdList.add(id);
        return id;
    }

    //  Notification을 취소
    private void cancelNotification() {
        if (mActiveIdList.isEmpty())
            return;
        int id = mActiveIdList.remove(0);
        mManager.cancel(id);
    }

    // 지정된 EditText에 문자가 입력되어 있지 않으면 True를 반환
    private boolean hasString(int rsrc) {
        EditText et = (EditText)findViewById(rsrc);
        return et.length() != 0;
    }

    // 지정된 EditText의 내용을 복구
    private String asString(int rsrc) {
        EditText et = (EditText)findViewById(rsrc);
        return et.getText().toString();
    }
    
    // 지정된 EditText 내용을 int로 변환
    private int asInt(int rsrc) {
        EditText et = (EditText)findViewById(rsrc);
        String s = et.getText().toString();
        try {
            return Integer.decode(s);
        } catch (NumberFormatException e) {
            Log.e("HelloNotification",e.getMessage());
        }
        return 0;
    }

    // 지정된 EditText 내용을 UTC로 변환
    private long asDateTime(int rsrc) {
        EditText et = (EditText)findViewById(rsrc);
        String s = et.getText().toString();
        try {
            SimpleDateFormat f =
                new SimpleDateFormat("yy/MM/dd HH:mm");
            return f.parse(s).getTime();
        } catch (ParseException e) {
            Log.e("HelloNotification",e.getMessage());
        }
        return System.currentTimeMillis();
    }
}
