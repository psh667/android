package com.androidside.viewflipperdemob2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;

public class ViewFilpperDemoB2 extends Activity implements View.OnClickListener {
    Button start;
    Button stop;
    ViewFlipper flipper;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
        
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        
        //플리핑 간격을 1초로 한다.
        flipper.setFlipInterval(1000);
    }

    @Override
    public void onClick(View v) {
        //start 버튼이 클릭되었을 때 플리핑을 시작한다.
        if (v == start) {
            flipper.startFlipping();
            
        //stop 버튼이 클릭되었을 때 플리핑을 멈춘다.
        } else if (v == stop) {
            flipper.stopFlipping();
        }        
    }
}