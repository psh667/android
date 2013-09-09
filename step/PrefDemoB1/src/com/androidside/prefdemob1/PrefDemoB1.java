package com.androidside.prefdemob1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 
public class PrefDemoB1 extends Activity implements View.OnClickListener {
    EditText wifi;
    EditText network;
    EditText bluetooth;
    EditText device;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        wifi = (EditText) findViewById(R.id.wifi);
        network = (EditText) findViewById(R.id.network);
        bluetooth = (EditText) findViewById(R.id.bluetooth);
        device = (EditText) findViewById(R.id.device);
        
        Button button = (Button) findViewById(R.id.Edit);        
        button.setOnClickListener(this);
    }
    
    @Override
    protected void onResume() {        
        super.onResume();
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        wifi.setText(new Boolean(prefs.getBoolean("wifi", false)).toString());
        network.setText(prefs.getString("network", "000"));
        bluetooth.setText(new Boolean(prefs.getBoolean("bluetooth", false)).toString());
        device.setText(prefs.getString("device", ""));
    }    
    
    public void onClick(View v) {
        Intent intent = new Intent(this, PrefEdit.class);
        startActivity(intent);
    }
}