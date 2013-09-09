package com.androidside.buttonclickdemoa3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ButtonClickDemoA3 extends Activity {    
    EditText edit;        
    TextView text;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        edit = (EditText) findViewById(R.id.edit);        
        text = (TextView) findViewById(R.id.text);        
    }
    
    public void clickButton(View v) {
        text.setText(edit.getText());        
    }
}