package com.androidside.imageviewdemoc1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageViewDemoC1 extends Activity {
    String[] icons = {"angel", "crying", "happy", "kissing"};
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
                
        for (int i = 0; i < icons.length; i++) {
            int resId = getResources().getIdentifier(icons[i], "drawable", "com.androidside.imageviewdemoc1");
            
            ImageView image = new ImageView(this);
            image.setImageResource(resId);
            
            layout.addView(image);
        }
    }
}