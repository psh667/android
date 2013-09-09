package com.androidside.toastdemob2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ToastDemoB2 extends Activity implements View.OnClickListener {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {                
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.toastlayout, null);        
        
        Toast toast = Toast.makeText(this, "TOAST!!!", Toast.LENGTH_SHORT);        
        toast.setView(layout);
        toast.show();
    }
}