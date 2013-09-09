package com.androidside.customdialodemoa1;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomDialogDemoA1 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                final Dialog dialog = new Dialog(CustomDialogDemoA1.this);
                dialog.setContentView(R.layout.logindialog);                
                dialog.setTitle("커스텀 다이얼로그");
                dialog.setCancelable(true);
                
                Button confirmButton = (Button) dialog.findViewById(R.id.confirm);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                    }
                });
                
                dialog.show();
            }
        });
    }
}