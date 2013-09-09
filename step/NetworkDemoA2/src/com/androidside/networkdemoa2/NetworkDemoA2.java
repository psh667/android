package com.androidside.networkdemoa2;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class NetworkDemoA2 extends Activity {
    TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        boolean state = isNetworkAvailable(this);
        String stateStr;
        
        if (state) {
            stateStr = "연결되어 있음";
        } else {
            stateStr = "연결되어 있지 않음";
        }            
        
        TextView stateText = (TextView) findViewById(R.id.state);
        stateText.setText(stateStr);
        
    }
    
    public static boolean isNetworkAvailable(Context context) {
        boolean available = false;
        
        ConnectivityManager manager = (ConnectivityManager) context
                         .getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo info = manager.getActiveNetworkInfo();
        
        if (info != null && info.isAvailable()) {
            available = true;
        }
        
        return available;
    }

}