package com.androidside.logdemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class LogDemoA1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.e("tag", "error message");
        Log.w("tag", "warning message");
        Log.i("tag", "information message");
        Log.d("tag", "debugging message");
        Log.v("tag", "verbose message");
    }
}