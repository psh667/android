package com.androidside.frameanidemoa1;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FrameAniDemoA1 extends Activity {
    AnimationDrawable animation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startAnimation();
            }
        });

        Button endButton = (Button) findViewById(R.id.stop_button);
        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopAnimation();
            }
        });

    }

    private void startAnimation() {
        ImageView imageView = (ImageView) findViewById(R.id.frame_ani_imageview);

        BitmapDrawable frame1 = (BitmapDrawable) getResources().getDrawable(R.drawable.plane1);
        BitmapDrawable frame2 = (BitmapDrawable) getResources().getDrawable(R.drawable.plane2);
        BitmapDrawable frame3 = (BitmapDrawable) getResources().getDrawable(R.drawable.plane3);
        BitmapDrawable frame4 = (BitmapDrawable) getResources().getDrawable(R.drawable.plane4);
        BitmapDrawable frame5 = (BitmapDrawable) getResources().getDrawable(R.drawable.plane5);
        BitmapDrawable frame6 = (BitmapDrawable) getResources().getDrawable(R.drawable.plane6);

        int duration = 400;
        animation = new AnimationDrawable();
        animation.setOneShot(false);
        animation.addFrame(frame1, duration);
        animation.addFrame(frame2, duration);
        animation.addFrame(frame3, duration);
        animation.addFrame(frame4, duration);
        animation.addFrame(frame5, duration);
        animation.addFrame(frame6, duration);

        imageView.setBackgroundDrawable(animation);
        animation.start();
    }

    private void stopAnimation() {
        animation.stop();
    }
}
