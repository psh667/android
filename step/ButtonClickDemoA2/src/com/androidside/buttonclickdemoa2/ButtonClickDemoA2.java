package com.androidside.buttonclickdemoa2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ButtonClickDemoA2 extends Activity implements View.OnClickListener {    
    EditText edit;        
    TextView text;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        edit = (EditText) findViewById(R.id.edit);        
        text = (TextView) findViewById(R.id.text);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) { 
        text.setText(edit.getText());
        
    }
}