package com.androidside.fontdemob1;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class FontDemoB1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView text = (TextView) findViewById(R.id.myfont);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/cour.ttf");        
        text.setTypeface(face);
    }
}