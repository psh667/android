package com.androidside.filedemoa1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FileDemoA1 extends Activity implements View.OnClickListener {
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
            br = new BufferedReader(new InputStreamReader(new FileInputStream("sdcard/text.txt")));
            
            String str = null;
            
            while ((str = br.readLine()) != null) {
                sb.append(str+"\n");
            }            
        } catch (Exception e) {            
            try {
                br.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        
        EditText editText = (EditText) findViewById(R.id.viewarea);
        editText.setText(sb.toString());
    }
}