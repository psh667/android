package com.msi.manning.chapter8.NotifyAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    public void onReceiveIntent(Context context, Intent intent) {
        Toast.makeText(context, R.string.app_name, Toast.LENGTH_SHORT).show();

        abortBroadcast();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
