package com.androidside.batteryinfodemoa1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

public class BatteryInfoDemoA1 extends Activity {
    private TextView pluggedText;
    private TextView statusText;
    private TextView levelText;

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        Intent intent = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            this.intent = intent;

            pluggedText.setText(getPlugged());
            statusText.setText(getStatus());
            levelText.setText(getLevel());
        }

        public String getPlugged() {
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        
            String pluggedStr = "";
        
            switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                pluggedStr = "BATTERY_PLUGGED_AC";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                pluggedStr = "BATTERY_PLUGGED_USB";
                break;
            }
            return pluggedStr;
        }
        
        public String getStatus() {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
        
            String statusStr = "";
        
            switch (status) {
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                statusStr = "BATTERY_STATUS_UNKNOWN";
                break;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusStr = "BATTERY_STATUS_CHARGING ";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                statusStr = "BATTERY_STATUS_DISCHARGING";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                statusStr = "BATTERY_STATUS_NOT_CHARGING ";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                statusStr = "BATTERY_STATUS_FULL ";
                break;
            }
        
            return statusStr;
        }
        
        public String getLevel() {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        
            return "" + level + "%";
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        pluggedText = (TextView) findViewById(R.id.plugged_text);
        statusText = (TextView) findViewById(R.id.status_text);
        levelText = (TextView) findViewById(R.id.level_text);

        registerReceiver(batteryReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batteryReceiver);
    }
}