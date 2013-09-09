/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appstudio.android.sample.ch_26_1;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.appstudio.android.sample.R;
import com.appstudio.android.sample.ch_26.ShapeHolder;

/**
 * This application demonstrates loading Animator objects from XML resources.
 */
public class InterpolateActivity extends Activity {


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interpolate_activity);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        final MyAnimationView animView = new MyAnimationView(this);
        container.addView(animView);

        Button starter = (Button) findViewById(R.id.startButton);
        starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                animView.startAnimation();
            }
        });
    }

    public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {

        private static final float BALL_SIZE = 100f;

        public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
        Animator animation = null;

        public MyAnimationView(Context context) {
            super(context);
            balls.add(ShapeHolder.createBall(0, 70));
            balls.add(ShapeHolder.createBall(70, 70));
            balls.add(ShapeHolder.createBall(140, 70));
            balls.add(ShapeHolder.createBall(210, 70));
            balls.add(ShapeHolder.createBall(280, 70));
            balls.add(ShapeHolder.createBall(350, 70));
            balls.add(ShapeHolder.createBall(420, 70));
            balls.add(ShapeHolder.createBall(490, 70));
  

        }

        private void createAnimation() {
            Context appContext = InterpolateActivity.this;

            if (animation == null) {
                ObjectAnimator anim0 = (ObjectAnimator) AnimatorInflater.
                        loadAnimator(appContext, R.animator.object_animator);
          
                anim0.setTarget(balls.get(0));
                anim0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        invalidate();
                    }
                });
                
                ObjectAnimator anim1 = (ObjectAnimator) AnimatorInflater.
                        loadAnimator(appContext, R.animator.object_animator);
          
                anim1.setTarget(balls.get(1));
                anim1.setInterpolator(new AccelerateInterpolator());
 
                ObjectAnimator anim2 = (ObjectAnimator) AnimatorInflater.
                        loadAnimator(appContext, R.animator.object_animator);
          
                anim2.setTarget(balls.get(2));
                anim2.setInterpolator(new DecelerateInterpolator());
          
                ObjectAnimator anim3 = (ObjectAnimator) AnimatorInflater.
                        loadAnimator(appContext, R.animator.object_animator);
          
                anim3.setTarget(balls.get(3));
                anim3.setInterpolator(new AccelerateDecelerateInterpolator());
                
                ObjectAnimator anim4 = (ObjectAnimator) AnimatorInflater.
                        loadAnimator(appContext, R.animator.object_animator);
          
                anim4.setTarget(balls.get(4));
                anim4.setInterpolator(new AnticipateInterpolator());
                
                
                ObjectAnimator anim5 = (ObjectAnimator) AnimatorInflater.
                        loadAnimator(appContext, R.animator.object_animator);
          
                anim5.setTarget(balls.get(5));
                anim5.setInterpolator(new AnticipateOvershootInterpolator());
                
                ObjectAnimator anim6 = (ObjectAnimator) AnimatorInflater.
                        loadAnimator(appContext, R.animator.object_animator);
          
                anim6.setTarget(balls.get(6));
                anim6.setInterpolator(new BounceInterpolator());
                
                ObjectAnimator anim7 = (ObjectAnimator) AnimatorInflater.
                        loadAnimator(appContext, R.animator.object_animator);
          
                anim7.setTarget(balls.get(7));
                anim7.setInterpolator(new OvershootInterpolator());
                
                
                
                animation = new AnimatorSet();
                ((AnimatorSet) animation).playTogether(anim0, anim1, anim2, anim3, anim4, anim5, anim6, anim7);
                
                

            }
 
        }

        public void startAnimation() {
            createAnimation();
            animation.start();
        }


        @Override
        protected void onDraw(Canvas canvas) {
            for (ShapeHolder ball : balls) {
                canvas.translate(ball.getX(), ball.getY());
                ball.getShape().draw(canvas);
                canvas.translate(-ball.getX(), -ball.getY());
            }
        }
        
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {

            invalidate();
           ShapeHolder ball = balls.get(0);
//            ball.setY((Float)animation.getAnimatedValue());
        }
    }
}