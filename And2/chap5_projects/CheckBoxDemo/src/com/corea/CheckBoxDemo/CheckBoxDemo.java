package com.corea.CheckBoxDemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CheckBoxDemo extends Activity {
	CheckBox cb; 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        cb=(CheckBox)findViewById(R.id.check);
        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
                if (isChecked) { 
                    cb.setText("This checkbox is: checked"); 
                } 
                else { 
                    cb.setText("This checkbox is: unchecked"); 
                }
            }
        });
    }
}