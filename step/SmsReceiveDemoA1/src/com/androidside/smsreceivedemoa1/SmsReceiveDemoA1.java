package com.androidside.smsreceivedemoa1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiveDemoA1 extends BroadcastReceiver {    
    private String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String sender = "";
        String message = "";
        String action = intent.getAction();
        
        if(action.equals(ACTION_SMS_RECEIVED)){            
            Bundle bundle = intent.getExtras();
            
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");                
                
                for(Object pdu : pdus){
                    SmsMessage smsMessage = 
                                    SmsMessage.createFromPdu((byte[]) pdu);
                    message += smsMessage.getMessageBody();
                    
                    if (sender.equals("")) sender = smsMessage.getOriginatingAddress();
                }
            }            
            Toast.makeText(context, sender+":"+message, Toast.LENGTH_LONG).show();
        }        
    }
}
