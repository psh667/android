package com.appstudio.android.sample.ch_23_2;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PhoneCallActivity extends Activity {
    private EditText mPhoneNumberEditText;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_call_activity);
        
        mPhoneNumberEditText = 
            (EditText)findViewById(R.id.phoneNumber);
    }
    
    public void mCallDialer(View v)  {
        Uri number = 
            Uri.parse("tel:" + mPhoneNumberEditText.getText());
        Intent intent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(intent);    	
    }
    
    public void mCallDirect(View v)  {
        Uri number = 
            Uri.parse("tel:" + mPhoneNumberEditText.getText());
        Intent intent = new Intent(Intent.ACTION_CALL, number);
        startActivity(intent);   	
    }    
}