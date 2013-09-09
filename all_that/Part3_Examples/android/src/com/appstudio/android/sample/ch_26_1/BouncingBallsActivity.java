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
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.appstudio.android.sample.R;
import com.appstudio.android.sample.ch_26.ShapeHolder;


public class BouncingBallsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bouncing_balls_activity);
        LinearLayout container = 
                (LinearLayout) findViewById(R.id.container);
        container.addView(new MyAnimationView(this));
    }

    public class MyAnimationView extends View {
        private static final int RED = 0xffFF8080;
        private static final int BLUE = 0xff8080FF;

        public final ArrayList<ShapeHolder> balls = 
                new ArrayList<ShapeHolder>();
        AnimatorSet animation = null;

        public MyAnimationView(Context context) {
            super(context);

            ValueAnimator colorAnim = 
                    ObjectAnimator.ofInt(this, "backgroundColor"
                                         , RED, BLUE);
            colorAnim.setDuration(3000);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.setRepeatCount(ValueAnimator.INFINITE);
            colorAnim.setRepeatMode(ValueAnimator.REVERSE);
            ObjectAnimator.setFrameDelay(0);
            colorAnim.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_DOWN &&
                 event.getAction() != MotionEvent.ACTION_MOVE) {
                return false;
            }
            ShapeHolder newBall = ShapeHolder.createBall(
                    event.getX(), event.getY());
            balls.add(newBall);

            // 공이 늘어났다 찌그러드는 애니메이션 효과
            float startY = newBall.getY();
            float endY = getHeight() - 50f;
            float h = (float)getHeight();
            float eventY = event.getY();
            int duration = (int)(500 * ((h - eventY)/h));
            ValueAnimator bounceAnim = ObjectAnimator.ofFloat(
                    newBall, "y", startY, endY);
            bounceAnim.setDuration(duration);
            bounceAnim.setInterpolator(
                    new AccelerateInterpolator());
            ValueAnimator squashAnim1 = ObjectAnimator.ofFloat(
                    newBall, "x", newBall.getX(),
                    newBall.getX() - 25f);
            squashAnim1.setDuration(duration/4);
            squashAnim1.setRepeatCount(1);
            squashAnim1.setRepeatMode(ValueAnimator.REVERSE);
            squashAnim1.setInterpolator(
                    new DecelerateInterpolator());
            ValueAnimator squashAnim2 = ObjectAnimator.ofFloat(
                    newBall, "width", newBall.getWidth(),
                    newBall.getWidth() + 50);
            squashAnim2.setDuration(duration/4);
            squashAnim2.setRepeatCount(1);
            squashAnim2.setRepeatMode(ValueAnimator.REVERSE);
            squashAnim2.setInterpolator(
                    new DecelerateInterpolator());
            ValueAnimator stretchAnim1 = ObjectAnimator.ofFloat(
                    newBall, "y", endY,
                    endY + 25f);
            stretchAnim1.setDuration(duration/4);
            stretchAnim1.setRepeatCount(1);
            stretchAnim1.setInterpolator(
                    new DecelerateInterpolator());
            stretchAnim1.setRepeatMode(ValueAnimator.REVERSE);
            ValueAnimator stretchAnim2 = ObjectAnimator.ofFloat(
                    newBall, "height",
                    newBall.getHeight(),newBall.getHeight()-25);
            stretchAnim2.setDuration(duration/4);
            stretchAnim2.setRepeatCount(1);
            stretchAnim2.setInterpolator(
                    new DecelerateInterpolator());
            stretchAnim2.setRepeatMode(ValueAnimator.REVERSE);
            ValueAnimator bounceBackAnim = ObjectAnimator.ofFloat(
                    newBall, "y", endY,
                    startY);
            bounceBackAnim.setDuration(duration);
            bounceBackAnim.setInterpolator(
                    new DecelerateInterpolator());

            AnimatorSet bouncer = new AnimatorSet();
            bouncer.play(bounceAnim).before(squashAnim1);
            bouncer.play(squashAnim1).with(squashAnim2);
            bouncer.play(squashAnim1).with(stretchAnim1);
            bouncer.play(squashAnim1).with(stretchAnim2);
            bouncer.play(bounceBackAnim).after(stretchAnim2);

            // 페이딩 애니메이션 
            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(
                    newBall, "alpha", 1f, 0f);
            fadeAnim.setDuration(250);
            fadeAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    balls.remove(((ObjectAnimator)animation)
                            .getTarget());
                }
            });

            // 애니메이션 셋의 애니메이션 셋
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(bouncer).before(fadeAnim);

            animatorSet.start();

            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (int i = 0; i < balls.size(); ++i) {
                ShapeHolder shapeHolder = balls.get(i);
                canvas.save();
                canvas.translate(
                        shapeHolder.getX(), shapeHolder.getY());
                shapeHolder.getShape().draw(canvas);
                canvas.restore();
            }
        }
    }
}