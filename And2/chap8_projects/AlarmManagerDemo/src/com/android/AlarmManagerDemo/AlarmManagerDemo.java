package com.android.AlarmManagerDemo;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

public class AlarmManagerDemo extends Activity {
	Toast mToast;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = new Intent(AlarmManagerDemo.this, AlarmService.class);
        PendingIntent sender = PendingIntent.getBroadcast(AlarmManagerDemo.this,
                0, intent, 0);
        
        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 10*1000;

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        firstTime, 10*1000, sender);

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        int mSecond =c.get(Calendar.SECOND);
        
  
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(AlarmManagerDemo.this, "Time is "+mHour+":"+mMinute+":"+mSecond,
                Toast.LENGTH_LONG);
        mToast.show();
    }
}