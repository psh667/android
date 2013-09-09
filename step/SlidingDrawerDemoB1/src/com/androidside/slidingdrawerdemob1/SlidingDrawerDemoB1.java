package com.androidside.slidingdrawerdemob1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SlidingDrawer;

public class SlidingDrawerDemoB1 extends Activity {    
    private SlidingDrawer drawer;
        
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        drawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
        
        drawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {
            @Override
            public void onScrollStarted() {
                Log.v("SlidingDrawerDemoB1", "onScrollStarted()");
            }
            @Override
            public void onScrollEnded() {
                Log.v("SlidingDrawerDemoB1", "onScrollEnded()");
            }
        });
        
        drawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                Log.v("SlidingDrawerDemoB1", "onDrawerOpened()");
            }
        });
        
        drawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                Log.v("SlidingDrawerDemoB1", "onDrawerClosed()");
            }
        });        
    }
    
}