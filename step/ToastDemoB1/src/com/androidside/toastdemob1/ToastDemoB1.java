package com.androidside.toastdemob1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ToastDemoB1 extends Activity implements View.OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ( (Button) findViewById(R.id.toast_top) ).setOnClickListener(this);
        ( (Button) findViewById(R.id.toast_center1) ).setOnClickListener(this);
        ( (Button) findViewById(R.id.toast_center2) ).setOnClickListener(this);
        ( (Button) findViewById(R.id.toast_center3) ).setOnClickListener(this);
        ( (Button) findViewById(R.id.toast_bottom) ).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.toast_top : viewToast(Gravity.TOP, 0, 0); break;
        case R.id.toast_center1 : viewToast(Gravity.CENTER, 0, 0); break;
        case R.id.toast_center2 : viewToast(Gravity.CENTER, 50, 50); break;
        case R.id.toast_center3 : viewToast(Gravity.CENTER, -50, -50); break;
        case R.id.toast_bottom : viewToast(Gravity.BOTTOM, 0, 0); break;        
        }
    }
    
    public void viewToast(int gravity, int xOffset, int yOffset) {
        Toast toast = Toast.makeText(this, "TOAST!!!", Toast.LENGTH_SHORT);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }
}