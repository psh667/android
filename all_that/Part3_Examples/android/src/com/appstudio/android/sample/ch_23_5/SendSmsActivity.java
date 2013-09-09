package com.appstudio.android.sample.ch_23_5;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SendSmsActivity extends Activity {
    private TextView mNumberTextView;
    private TextView mMessageTextView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_sms_activity);
        mNumberTextView = (TextView)findViewById(R.id.number);
        mMessageTextView =(TextView)findViewById(R.id.message);
    }
    
    public void mSendMessage(View v)  {
        String number = mNumberTextView.getText().toString();
        String message = mMessageTextView.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:"+number));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }
}