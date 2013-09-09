package com.androidside.toucheventdemoa1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchEventDemoA1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        MyView myView = new MyView(this);
        myView.setOnTouchListener(new View.OnTouchListener() {            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d("tag", "OnTouchListener.onTouch");
                    
                    return false;
                }
                return true;
            }
        });
        
        setContentView(myView);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("tag", "Activity.onTouchEvent");
            
            return false;
        }
        return true;
    }
    
}

class MyView extends View {
    public MyView(Context context) {
        super(context);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("tag", "MyView.onTouchEvent");
            
            return false;
        }
        return true;
    }    
}

