package com.msi.manning.telephonyexplorer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * IntentReceiver for SMS_RECEIVED that invokes SmsExample Activity.
 * 
 * @author charliecollins
 * 
 */
public class SmsReceiver extends BroadcastReceiver {

    public static final String SMSRECEIVED = "SMSR";
    private static final String SMS_REC_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.v(Constants.LOGTAG, "SmsReceiver onReceive");
        if (intent.getAction().equals(SmsReceiver.SMS_REC_ACTION)) {
            Log.v(Constants.LOGTAG, "SmsReceiver SMS received");
            StringBuilder sb = new StringBuilder();

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    sb.append("body - " + smsMessage.getDisplayMessageBody());
                }
            }
            Toast.makeText(context, "SMS RECEIVED - " + sb.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
