package com.androidside.smssenddemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SmsSendDemoA1 extends Activity {
    EditText receiver;
    EditText message;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        receiver = (EditText) findViewById(R.id.receiver);
        message = (EditText) findViewById(R.id.message);
        
        Button button = (Button) findViewById(R.id.button);
        
        button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(receiver.getText().toString(), null, message.getText().toString(), null, null);  
            }
        });        
    }
}