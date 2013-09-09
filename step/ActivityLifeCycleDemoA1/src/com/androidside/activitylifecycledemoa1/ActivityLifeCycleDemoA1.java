package com.androidside.activitylifecycledemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActivityLifeCycleDemoA1 extends Activity {   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.d("Activity", "onCreate()");
        
        Button finishButton = (Button) findViewById(R.id.finish);
        finishButton.setOnClickListener(new View.OnClickListener() {            
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        Log.d("Activity", "onSaveInstanceState()");
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        
        Log.d("Activity", "onRestoreInstanceState()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Activity", "onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Activity", "onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Activity", "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Activity", "onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Activity", "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Activity", "onStop()");
    }
}