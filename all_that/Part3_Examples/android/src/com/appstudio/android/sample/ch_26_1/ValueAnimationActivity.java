package com.appstudio.android.sample.ch_26_1;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.appstudio.android.sample.R;
import com.appstudio.android.sample.ch_26.ShapeHolder;

public class ValueAnimationActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.value_animation_activity);
        LinearLayout container = 
                (LinearLayout) findViewById(R.id.container);
        final MyAnimationView animView = 
                new MyAnimationView(this);
        container.addView(animView);

        Button starter = 
                (Button) findViewById(R.id.startButton);
        starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                animView.startAnimation();
            }
        });
    }

    public class MyAnimationView extends View {
        private ShapeHolder mBall;
        ValueAnimator mRunner;

        public MyAnimationView(Context context) {
            super(context);
            mBall = ShapeHolder.createBall(50, 50);

        }

        private void createRunner() {
            if (mRunner == null) {
            	mRunner = ValueAnimator.ofFloat(mBall.getY()
            	             , getHeight() - mBall.getHeight());
            	mRunner.setDuration(1000);
            	mRunner.setRepeatCount(1);
            	mRunner.setRepeatMode(ValueAnimator.REVERSE);
                
            	mRunner.addUpdateListener(
            	        new ValueAnimator
            	                     .AnimatorUpdateListener() {
                    public void onAnimationUpdate(
                            ValueAnimator animation) {
                        mBall.setY((Float) animation
                                .getAnimatedValue());
                        invalidate();
                    }
                });
            }
        }

        public void startAnimation() {
            createRunner();
            mRunner.start();
        }
 
        @Override
        protected void onDraw(Canvas canvas) {
        	canvas.translate(mBall.getX(), mBall.getY());
        	mBall.getShape().draw(canvas);
        	canvas.translate(-mBall.getX(), -mBall.getY());
            }
        }
 }