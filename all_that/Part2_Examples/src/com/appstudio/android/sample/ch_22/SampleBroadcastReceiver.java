package com.appstudio.android.sample.ch_22;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SampleBroadcastReceiver extends BroadcastReceiver {

	String TAG = "BroadcastReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
			Log.d(TAG, "BroadcastReceiver onReceive()");
			
			Bundle bundle = intent.getExtras();
			String message = "";
            String sender = "";
            
			if(bundle != null){
				Object[] pdusObj = (Object[]) bundle.get("pdus");
                
				for (Object pdu : pdusObj) {
                    SmsMessage part = SmsMessage.createFromPdu((byte[])pdu);
                    message += part.getDisplayMessageBody();
                    if (sender == null) {
                        sender = part.getDisplayOriginatingAddress();
                    }
                }
			}
			Toast.makeText(context, message+"\n: "+sender, Toast.LENGTH_SHORT).show();
		}else if(intent.getAction().equals(BroadcastActivity.USER_DEFINED_MSG)){
			Log.d(TAG, "BroadcastReceiver onReceive() MSG");
			Toast.makeText(context, intent.getStringExtra("data"), Toast.LENGTH_SHORT).show();
		}
	}
}
