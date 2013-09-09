package com.androidside.titlebardemoa2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class TitleBarDemoA2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);
        
        setProgressBarIndeterminateVisibility(true);
    }
}