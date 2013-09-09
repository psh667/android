package com.androidside.filedemoa3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FileDemoA3 extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) findViewById(R.id.fileview); 
        button.setOnClickListener(this);
    }

    public void onClick(View v) { 
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {            
            br = new BufferedReader(new InputStreamReader(openFileInput("text.txt")));
            
            String str = null;
            
            while((str = br.readLine()) != null) {
                sb.append(str+"\n");
            }
            br.close();
            
        } catch (Exception e) {
        }
        
        EditText editText = (EditText) findViewById(R.id.viewarea);
        editText.setText(sb.toString());
    }
}