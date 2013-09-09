package com.msi.manning.chapter8.SMSNotifyExample2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSNotifyExample extends BroadcastReceiver {

    private static final String LOG_TAG = "SMSReceiver";

    public static final int NOTIFICATION_ID_RECEIVED = 0x1221;

    static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    public void onReceiveIntent(Context context, Intent intent) {

        if (intent.getAction().equals(SMSNotifyExample.ACTION)) {
            StringBuilder sb = new StringBuilder();

            Bundle bundle = intent.getExtras();
            if (bundle != null) {

                Object[] pdusObj = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdusObj.length];

                for (SmsMessage currentMessage : messages) {
                    sb.append("Received SMS\nFrom: ");
                    sb.append(currentMessage.getDisplayOriginatingAddress());
                    sb.append("\n----Message----\n");
                    sb.append(currentMessage.getDisplayMessageBody());

                }
            }
            Log.i(SMSNotifyExample.LOG_TAG, "[SMSApp] onReceiveIntent: " + sb);
            Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
