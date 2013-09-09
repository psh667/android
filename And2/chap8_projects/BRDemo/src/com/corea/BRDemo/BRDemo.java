package com.corea.BRDemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BRDemo extends BroadcastReceiver {
    /** Called when the activity is first created. */
    @Override
    public void onReceive(Context content, Intent intent) {
    	Log.i("BRDemo", "RECEIVED_BOOT_COMPLETED");
    }
}