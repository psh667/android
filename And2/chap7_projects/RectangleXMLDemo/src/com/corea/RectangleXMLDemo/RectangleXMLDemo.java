package com.corea.RectangleXMLDemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class RectangleXMLDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    	ImageView imageView=(ImageView)findViewById(R.id.ImageView01);
        imageView.setImageResource(R.drawable.rect);
    }
}