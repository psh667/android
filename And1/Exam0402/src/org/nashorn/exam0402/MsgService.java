package org.nashorn.exam0402;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;

public class MsgService extends Service {
    private String phoneNumber = "";
    private String messageText = "";    
    
    public String getPhoneNumber()
    {
    	return this.phoneNumber;
    }
    
    public String getMessageText()
    {
    	return this.messageText;
    }
    
    @Override
    public void onCreate() {
    }
    
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(MsgReceiver, filter);
    }
    
    BroadcastReceiver MsgReceiver = new BroadcastReceiver() {
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
    		{
	            Bundle bundle = intent.getExtras();        
	            if (bundle != null)
	            {
	                Object[] pdus = (Object[]) bundle.get("pdus");
	                SmsMessage[] msgs = new SmsMessage[pdus.length];
	                for (int i=0; i<msgs.length; i++){
	                    msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
	                    phoneNumber = msgs[i].getOriginatingAddress();
	                    messageText = msgs[i].getMessageBody().toString();
	                    break;
	                }
	            }
    		}
        }
    };    
    
    @Override
    public void onDestroy() {
        super.onDestroy();
     }

    private final IBinder binder = new MyBinder();
    
    public class MyBinder extends Binder
    {
    	MsgService getService()
    	{
    		return MsgService.this;
    	}
    }
    
    @Override
    public IBinder onBind(Intent intent) {
    	return binder;
    }
}