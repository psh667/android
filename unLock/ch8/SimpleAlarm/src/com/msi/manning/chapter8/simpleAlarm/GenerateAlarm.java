package com.msi.manning.chapter8.simpleAlarm;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GenerateAlarm extends Activity {

    Toast mToast;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        Button button = (Button) findViewById(R.id.set_alarm_button);
        button.setOnClickListener(this.mOneShotListener);
    }

    private OnClickListener mOneShotListener = new OnClickListener() {

        public void onClick(View v) {

            Intent intent = new Intent(GenerateAlarm.this, AlarmReceiver.class);

            PendingIntent appIntent = PendingIntent.getBroadcast(GenerateAlarm.this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 30);

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), appIntent);

            if (GenerateAlarm.this.mToast != null) {
                GenerateAlarm.this.mToast.cancel();
            }
            GenerateAlarm.this.mToast = Toast.makeText(GenerateAlarm.this, R.string.alarm_message, Toast.LENGTH_LONG);
            GenerateAlarm.this.mToast.show();
        }
    };

}
