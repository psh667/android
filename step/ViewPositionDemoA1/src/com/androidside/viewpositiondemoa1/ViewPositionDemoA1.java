package com.androidside.viewpositiondemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;

public class ViewPositionDemoA1 extends Activity {
    ImageView image;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        image = (ImageView) findViewById(R.id.image);
    }    

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {            
            Window window = this.getWindow();
            int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
            
            int left = (int) event.getX();
            int top = (int) event.getY() - contentViewTop;

            image.layout(left, top, left + image.getWidth(), top + image.getHeight());
        }

        return super.onTouchEvent(event);
    }
}