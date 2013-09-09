package com.androidside.telephonyinfodemoa1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelephonyInfoDemoA1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final TextView text = (TextView) findViewById(R.id.text);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {            
            @Override
            public void onClick(View v) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                
                String str = "";
                str += "NetworkOperatorName:" + tm.getNetworkOperatorName() + "\n";
                str += "NetworkType:" + tm.getNetworkType() + "\n";
                str += "Line1Number:" + tm.getLine1Number() + "\n";
                str += "SimOperatorName:" + tm.getSimOperatorName() + "\n";
                str += "PhoneType:" + tm.getPhoneType() + "\n";          
                
                text.setText(str);
            }
        });
    }
}