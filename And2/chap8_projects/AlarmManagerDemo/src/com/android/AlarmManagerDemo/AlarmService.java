package com.android.AlarmManagerDemo;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmService extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        int mSecond =c.get(Calendar.SECOND);        
        
        Toast.makeText(context, "Alarm Time is "+mHour+": "+mMinute+":"+mSecond, Toast.LENGTH_SHORT).show();
	}
}
