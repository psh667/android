package com.androidside.savedinstancedemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SavedInstanceDemoA1 extends Activity {
    TextView text;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        text = (TextView) findViewById(R.id.text);
                
        if (savedInstanceState != null) {
            text.setText(savedInstanceState.getString("message"));
        }        
    }
        
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        outState.putString("message", "¹Ý°©½À´Ï´Ù!!");
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        
        text.setText(savedInstanceState.getString("message"));
    }
}