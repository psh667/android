package com.androidside.textviewdemoc2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TextViewDemoC2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String str = "";
        for (int i = 1; i < 7; i++) {
            int resID = getResources().getIdentifier("str"+i, "string", "com.androidside.textviewdemoc2");
            
            str += getString(resID);            
        }
        
        ((TextView) findViewById(R.id.text)).setText(str);
    }
}