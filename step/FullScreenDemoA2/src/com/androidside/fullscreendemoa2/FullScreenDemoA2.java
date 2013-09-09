package com.androidside.fullscreendemoa2;

import android.app.Activity;
import android.os.Bundle;

public class FullScreenDemoA2 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
    }
}