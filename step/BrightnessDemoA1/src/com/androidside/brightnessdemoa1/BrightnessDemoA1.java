package com.androidside.brightnessdemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class BrightnessDemoA1 extends Activity implements View.OnClickListener{
    Button minButton;
    Button maxButton;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        minButton = (Button) findViewById(R.id.min);
        maxButton = (Button) findViewById(R.id.max);
        
        minButton.setOnClickListener(this);
        maxButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        float bright = 0f;
        
        if (v == minButton) {
            bright = 0.1f;
        } else if (v == maxButton) {
            bright = 1f;
        }
        
        WindowManager.LayoutParams params = getWindow().getAttributes();
        
        params.screenBrightness = bright;
        getWindow().setAttributes(params);        
    }
}


