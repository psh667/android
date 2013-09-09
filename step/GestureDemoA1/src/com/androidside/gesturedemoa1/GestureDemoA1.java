package com.androidside.gesturedemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;

public class GestureDemoA1 extends Activity implements OnGestureListener, OnDoubleTapListener {
    private GestureDetector detector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        detector = new GestureDetector(this, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        this.detector.onTouchEvent(e);
        return super.onTouchEvent(e);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("Gesture", "onDown " + e.toString());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("Gesture", "onShowPress " + e.toString());
    }
    
    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("Gesture", "onLongPress " + e.toString());
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("Gesture", "onFling " + e1.toString() + "," + e2.toString());
        return false;
    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("Gesture", "onScroll " + e1.toString() + "," + e2.toString());
        return false;
    }
    
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("Gesture", "onSingleTapUp " + e.toString());
        return false;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d("Gesture", "onSingleTapConfirmed " + e.toString());
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("Gesture", "onDoubleTap " + e.toString());
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d("Gesture", "onDoubleTapEvent " + e.toString());
        return false;
    }
}
