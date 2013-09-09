package com.androidside.smssenddemob1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SmsSendDemoB1 extends Activity {
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
                /*Uri receiverUri = Uri.parse("smsto:"+receiver.getText().toString()); 
                
                Intent intent = new Intent(Intent.ACTION_VIEW, receiverUri);  
                intent.putExtra("sms_body", message.getText().toString());
                startActivity(intent);
                
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms://"));
                intent.putExtra("address", receiver.getText().toString());
                intent.putExtra("sms_body", message.getText().toString());
                startActivity(intent);*/
                
                
                Uri mmsUri = Uri.parse("content://media/external/images/media/1");   
                
                //Intent intent = new Intent(Intent.ACTION_SEND);
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mms://"));
                intent.putExtra("address", receiver.getText().toString());
                intent.putExtra("sms_body", message.getText().toString());
                intent.putExtra(Intent.EXTRA_STREAM, mmsUri);   
                intent.setType("image/png");   
                startActivity(intent); 
                
            }
        });        
    }
}