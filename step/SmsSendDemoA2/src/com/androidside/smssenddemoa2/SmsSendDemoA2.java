package com.androidside.smssenddemoa2;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsSendDemoA2 extends Activity implements View.OnClickListener {
    String SEND_INTENT = "com.androidside.SENT_SMS";
    String DELIVERY_INTENT = "com.androidside.DELIVERY_SMS";
    
    PendingIntent sentIntent;
    PendingIntent deliveryIntent;
    
    EditText receiver;
    EditText message;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        receiver = (EditText) findViewById(R.id.receiver);
        message = (EditText) findViewById(R.id.message);        
        Button button = (Button) findViewById(R.id.button);
        
        button.setOnClickListener(this);    
        
        
        sentIntent = PendingIntent.getBroadcast(this, 0, new Intent(SEND_INTENT), 0);
        deliveryIntent = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERY_INTENT), 0);
        
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == Activity.RESULT_OK) {
                    Toast.makeText(getBaseContext(), "전송 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "전송 실패", Toast.LENGTH_SHORT).show();
                }
            }            
        }, new IntentFilter(SEND_INTENT));
        
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == Activity.RESULT_OK) {
                    Toast.makeText(getBaseContext(), "전달 성공", Toast.LENGTH_SHORT).show();
                } else if (getResultCode() == Activity.RESULT_CANCELED) {                    
                    Toast.makeText(getBaseContext(), "전달 실패", Toast.LENGTH_SHORT).show();
                } else {                    
                    Toast.makeText(getBaseContext(), "전달 실패" + getResultCode(), Toast.LENGTH_SHORT).show();
                }
                
                Log.d("tag", "delivery " + getResultCode());
            }
            
        }, new IntentFilter(DELIVERY_INTENT));
    }

    @Override
    public void onClick(View v) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(receiver.getText().toString(), null, message.getText().toString(), sentIntent , deliveryIntent );
    }
}