package com.androidside.displaydemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class DisplayDemoA1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView text = (TextView) findViewById(R.id.text);
        
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        
        text.setText("(width, height) : (" + width + ", " + height + ")"); 
        
    }
}