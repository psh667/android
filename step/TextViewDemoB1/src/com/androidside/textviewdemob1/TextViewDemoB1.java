package com.androidside.textviewdemob1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TextViewDemoB1 extends Activity {
    public void onCreate(Bundle savedInstanceState) 
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        for (int i = 1; i < 4; i++) {
            int resID = getResources().getIdentifier("com.androidside:id/textview"+i, null, null);
            ((TextView) findViewById(resID)).setText("test"+i);
        }
        
        
//int resID = getResources().getIdentifier("textview2", "id", "com.androidside");
        
    }
}
