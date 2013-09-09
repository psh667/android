package com.androidside.telephonydemoa1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class TelephonyDemoA1 extends BroadcastReceiver {
    MyPhoneStateListener phoneStateListener;
    TelephonyManager telephonyMgr;
    
    @Override
    public void onReceive(Context context, Intent intent) {
        phoneStateListener = new MyPhoneStateListener();
        telephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyMgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
 
    class MyPhoneStateListener extends PhoneStateListener {
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("telephony", "CALL_STATE_RINGING - " + incomingNumber);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("telephony", "CALL_STATE_OFFHOOK - " + incomingNumber);
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("telephony", "CALL_STATE_IDLE - " + incomingNumber);
                break;
            }
            telephonyMgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }
}