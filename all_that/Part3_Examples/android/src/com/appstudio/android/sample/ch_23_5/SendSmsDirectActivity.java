package com.appstudio.android.sample.ch_23_5;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;

public class SendSmsDirectActivity extends Activity {
    private TextView mNumberTextView;
    private TextView mMessageTextView;
    
    private TextView mSentTextView;
    private TextView mDeliveryTextView;

    final private static String MESSAGE_SENT_ACTION = 
        "com.appstudio.MESSAGE_SENT";
    final private static String MESSAGE_DELIVERY_ACTION = 
        "com.appstudio.MESSAGE_DELIVERY";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_sms_direct_activity);
        mNumberTextView = (TextView)findViewById(R.id.number);
        mMessageTextView =(TextView)findViewById(R.id.message);
        mSentTextView = (TextView)findViewById(R.id.sent);
        mDeliveryTextView = 
            (TextView)findViewById(R.id.delivery);
    }
    
    @Override
    protected void onPause() {
         super.onPause();
         unregisterReceiver(mSentBR);
         unregisterReceiver(mDeliveryBR);
    }

    @Override
    protected void onResume() {
         super.onResume();
         registerReceiver(mSentBR, 
                 new IntentFilter(MESSAGE_SENT_ACTION));
         registerReceiver(mDeliveryBR, 
                 new IntentFilter(MESSAGE_DELIVERY_ACTION));
    }

    public void mSendMessage(View v)  {
        String number = mNumberTextView.getText().toString();
        String message = mMessageTextView.getText().toString();
        mSentTextView.setText("나의 송신대기");
        mDeliveryTextView.setText("상대방 수신대기");
        SmsManager smsManager = SmsManager.getDefault();
        
        PendingIntent sentIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(MESSAGE_SENT_ACTION), 0);
        PendingIntent deliveryIntent = 
            PendingIntent.getBroadcast(this, 0, 
                    new Intent(MESSAGE_DELIVERY_ACTION), 0);
        
        smsManager.sendTextMessage(number, null, message, 
                sentIntent, deliveryIntent);
    }
    
    private BroadcastReceiver mSentBR = 
        new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode()==Activity.RESULT_OK)  {
                mSentTextView.setText("메세지 송신 성공");
            }else {
                mSentTextView.setText("메세지 송신 실패");
            }
        }
    };
    
    private BroadcastReceiver mDeliveryBR = 
        new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode()==Activity.RESULT_OK)  {
                mDeliveryTextView.setText(
                        "상대방이 메세지 수신에 성공했습니다.");
            }else {
                mDeliveryTextView.setText(
                        "상대방이 메세지 수신에 실패했습니다.");
            }
        }
    };    
}