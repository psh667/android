package com.androidside.titlebardemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class TitleBarDemoA1 extends Activity {    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.main);
        
        setProgress(5000);
        setProgressBarVisibility(true);
    }
}