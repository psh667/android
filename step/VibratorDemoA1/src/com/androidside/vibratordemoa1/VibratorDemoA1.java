package com.androidside.vibratordemoa1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class VibratorDemoA1 extends Activity implements View.OnClickListener {
    Button simpleButton;
    Button patternButton;
    Button cancelButton;
    Vibrator vibrator;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        simpleButton = (Button) findViewById(R.id.simple);
        patternButton = (Button) findViewById(R.id.pattern);
        cancelButton = (Button) findViewById(R.id.cancel);
        
        simpleButton.setOnClickListener(this);
        patternButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onClick(View v) {
        if (v == simpleButton) {
            vibrator.vibrate(1000);
        } else if (v == patternButton) {
            long[] pattern = {0L, 100L, 0L, 100L};
            vibrator.vibrate(pattern, 2);
        }  else if (v == cancelButton) {
            vibrator.cancel();
        }
        
    }
}