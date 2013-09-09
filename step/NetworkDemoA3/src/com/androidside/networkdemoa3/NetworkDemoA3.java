package com.androidside.networkdemoa3;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

public class NetworkDemoA3 extends Activity {
    TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        changeWifi();
    }
    
    private void changeWifi() {
        WifiManager wifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        
        if (wifiMgr.isWifiEnabled()) {
            wifiMgr.setWifiEnabled(false);
        } else {
            wifiMgr.setWifiEnabled(true);
        }
    }
}