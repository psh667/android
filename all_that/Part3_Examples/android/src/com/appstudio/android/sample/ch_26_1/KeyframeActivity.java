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
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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

public class KeyframeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyframe_activity);
        LinearLayout container = 
                (LinearLayout) findViewById(R.id.container);
        final MyAnimationView animView = 
                new MyAnimationView(this);
        container.addView(animView);

        Button starter = (Button)findViewById(R.id.startButton);
        starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                animView.startAnimation();
            }
        });

    }

    public class MyAnimationView extends View 
               implements ValueAnimator.AnimatorUpdateListener {
        AnimatorSet animation = null;
        ObjectAnimator yxBouncer = null;
        ShapeHolder ball = null;

        public MyAnimationView(Context context) {
            super(context);
            ball = ShapeHolder.createBall(350, 0);
        }

        private void createAnimation() {
            if (yxBouncer == null) {
                 PropertyValuesHolder pvhY = 
                         PropertyValuesHolder.ofFloat("y", 
                                 ball.getY(), getHeight() 
                                 - ball.getWidth());
                 
                float ballX = ball.getX();
                Keyframe kf0 = Keyframe.ofFloat(0f, ballX);
                Keyframe kf1 = Keyframe.ofFloat(.5f,ballX+100f);
                Keyframe kf2 = Keyframe.ofFloat(1f,ballX + 50f);
                PropertyValuesHolder pvhX = PropertyValuesHolder
                        .ofKeyframe("x", kf0, kf1, kf2);
                yxBouncer = ObjectAnimator
                        .ofPropertyValuesHolder(ball, pvhY,
                                        pvhX).setDuration(1000);
                yxBouncer.setRepeatCount(1);
                yxBouncer.setRepeatMode(ValueAnimator.REVERSE);
                yxBouncer.addUpdateListener(this);
            }
        }

        public void startAnimation() {
            createAnimation();
            yxBouncer.start();
        }
        @Override
        protected void onDraw(Canvas canvas) {
        	canvas.translate(ball.getX(), ball.getY());
        	ball.getShape().draw(canvas);
        	canvas.translate(-ball.getX(), -ball.getY());
        }
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }
    }
}