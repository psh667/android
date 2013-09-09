package com.corea.ToggleButtonDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ToggleButtonDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final ToggleButton toggleB = (ToggleButton) findViewById(R.id.togglebutton);
        toggleB.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	TextView tv = (TextView)findViewById(R.id.textview01);
                if (toggleB.isChecked()) {
                    Toast.makeText(ToggleButtonDemo.this, "»∞º∫µ ", Toast.LENGTH_SHORT).show();
                    tv.setText("Toggle button is enabled");
                } else {
                    Toast.makeText(ToggleButtonDemo.this, "∫Ò»∞º∫µ ", Toast.LENGTH_SHORT).show();
                    tv.setText("Toggle button is disabled");	
                }
            }
        });        
    }
}