package com.androidside.alertdialogdemoa1;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlertDialogDemoA1 extends Activity implements View.OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);        
    }
                                                    sdfsf
    @Override
    public void onClick(View arg0) {
        new AlertDialog.Builder(this)
        .setIcon(R.drawable.icon)
        .setTitle("¿ŒªÁ")
        .setMessage("æ»≥Á«œººø‰!!")
        .setNeutralButton("¥›±‚", null)
        .show();
    }
}