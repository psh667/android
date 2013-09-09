package com.androidside.multitouchdemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class MultiTouchDemoA1 extends Activity {
    TextView pointerText1;
    TextView pointerText2;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        pointerText1 = (TextView) findViewById(R.id.pointer1);
        pointerText2 = (TextView) findViewById(R.id.pointer2);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        
        if (event.getAction() != MotionEvent.ACTION_UP) { 
            if (pointerCount == 1) {
                pointerText1.setText("("+event.getX(0)+","+event.getY(0)+")");
                pointerText2.setText("");
                
            } else if (pointerCount == 2) {
                pointerText1.setText("");
                pointerText2.setText("("+event.getX(0)+","+event.getY(0)+
                                    ")-("+event.getX(1)+","+event.getY(1)+")");
            }
        }

        return super.onTouchEvent(event);
    }
}