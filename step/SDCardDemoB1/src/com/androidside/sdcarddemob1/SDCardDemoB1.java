package com.androidside.sdcarddemob1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class SDCardDemoB1 extends Activity {
    TextView text;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        text = (TextView) findViewById(R.id.text);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        filter.addAction(Intent.ACTION_MEDIA_NOFS);
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        filter.addDataScheme("file");
        
        registerReceiver(sdcardReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();      
        
        unregisterReceiver(sdcardReceiver);
    }
    
    BroadcastReceiver sdcardReceiver = new BroadcastReceiver() {        
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            
            if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
                //内靛 累己
            } else if (action.equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
                //内靛 累己
            } else if (action.equals(Intent.ACTION_MEDIA_REMOVED)) {
                //内靛 累己
            } else if (action.equals(Intent.ACTION_MEDIA_EJECT)) {
                //内靛 累己
            } else if (action.equals(Intent.ACTION_MEDIA_NOFS)) {
                //内靛 累己    
            } else if (action.equals(Intent.ACTION_MEDIA_SCANNER_STARTED)) {
                //内靛 累己   
            } else if (action.equals(Intent.ACTION_MEDIA_SCANNER_FINISHED)) {
                //内靛 累己  
            } else if (action.equals(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)) {
                //内靛 累己
            } 
            text.setText(text.getText() + "\n" + action);
        }
    };
}