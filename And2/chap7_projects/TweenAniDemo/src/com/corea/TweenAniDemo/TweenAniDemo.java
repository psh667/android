package com.corea.TweenAniDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class TweenAniDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tween);
        
        // Handle Grow Button
        final Button scaleButton = (Button) findViewById(R.id.ButtonScale);
        scaleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performAnimation(R.anim.scale);
            }
        });
        
        // Handle Move Button
        final Button moveButton = 
          (Button)findViewById(R.id.ButtonTranslate);
        moveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performAnimation(R.anim.translate);
            }
        });
            
        // Handle Spin Button
        final Button spinButton = (Button) findViewById(R.id.ButtonRotate);
        spinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performAnimation(R.anim.rotate);
            }
        });
    }

    private void performAnimation(int animationResourceID)
    {
        // We will animate the imageview
        ImageView reusableImageView = (ImageView)
          findViewById(R.id.ImageView01);
        reusableImageView.setImageResource(R.drawable.red_rect);
        reusableImageView.setVisibility(View.VISIBLE);
        
        // Load the appropriate animation
         Animation an =  
           AnimationUtils.loadAnimation(this, animationResourceID);
        // Register a listener, so we can disable and re-enable buttons
        an.setAnimationListener(new AniListener());    
        // Start the animation
        reusableImageView.startAnimation(an);    
    }

    private void toggleButtons(boolean clickableState)
    {
        // Handle Scale Button
        final Button growButton = (Button) findViewById(R.id.ButtonScale);
        growButton.setClickable(clickableState);
        
        // Handle Move Button
        final Button moveButton = (Button)findViewById(R.id.ButtonTranslate);
        moveButton.setClickable(clickableState);
        
        // Handle Spin Button
        final Button spinButton = (Button) findViewById(R.id.ButtonRotate);
        spinButton.setClickable(clickableState);
    }

    class AniListener implements Animation.AnimationListener {

        public void onAnimationEnd(Animation animation) {
            // Hide our ImageView
            ImageView reusableImageView = (ImageView)
              findViewById(R.id.ImageView01);
            reusableImageView.setVisibility(View.INVISIBLE);
            
            // Enable all buttons once animation is over
            toggleButtons(true);
        }

        public void onAnimationRepeat(Animation animation) {
            // what to do when animation loops 
        }

        public void onAnimationStart(Animation animation) {
            // Disable all buttons while animation is running
            toggleButtons(true);
        }
    }
}