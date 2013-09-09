package com.androidside.textviewdemoc1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TextViewDemoC1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        for (int i = 1; i < 4; i++) {
            //int resID = getResources().getIdentifier("com.androidside.textviewdemoc1:id/textview"+i, null, null);
            int resID = getResources().getIdentifier("textview"+i, 
                                        "id", "com.androidside.textviewdemoc1");
            ((TextView) findViewById(resID)).setText("ÅØ½ºÆ®ºä "+i);
        }
    }
}