package com.androidside.telephonydemoa2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class TelephonyDemoA2 extends BroadcastReceiver {
    Context context; 

    MyPhoneStateListener phoneStateListener;
    TelephonyManager telephony;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        phoneStateListener = new MyPhoneStateListener();
        telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneStateListener,
                PhoneStateListener.LISTEN_CALL_STATE);
    }

    class MyPhoneStateListener extends PhoneStateListener {
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("telephony", "CALL_STATE_RINGING - " + incomingNumber);
                Toast.makeText(context, "incomingName " + getName(incomingNumber),
                        Toast.LENGTH_LONG).show();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("telephony", "CALL_STATE_OFFHOOK - " + incomingNumber);
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("telephony", "CALL_STATE_IDLE - " + incomingNumber);
                break;
            }
            telephony.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        } 

        public String getName(String incomingNumber) {
            String incomingName = "";
            
            //선택할 데이터
            String[] projection = new String[] { PhoneLookup.DISPLAY_NAME };

            Uri uri = Uri.withAppendedPath(
                    PhoneLookup.CONTENT_FILTER_URI, Uri.encode(incomingNumber));
            
            //지정된 uri에서 지정된 projection의 데이터의 커서를 구한다.
            Cursor cursor = context.getContentResolver().query(uri, projection,
                    null, null, null); 

            //커서의 위치를 첫 번째로 이동한다.
            if (cursor.moveToFirst()) {
                //커서에서 주소록에 등록된 이름을 얻는다.
                int nameIdx = cursor
                        .getColumnIndex(PhoneLookup.DISPLAY_NAME);
                incomingName = cursor.getString(nameIdx);                
            } else {
                incomingName = "NONE";
            }

            Log.d("telephony", "incomingName " + incomingName);
            
            return incomingName;
        }
    }

}