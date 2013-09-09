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

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
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

public class CustomEvaluatorActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_evaluator_activity);
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


    public class XYEvaluator implements TypeEvaluator<Object> {
        public Object evaluate(float fraction, Object startValue
                                            , Object endValue) {
            XYHolder startXY = (XYHolder) startValue;
            XYHolder endXY = (XYHolder) endValue;
            float x = startXY.getX() + fraction 
                    * (endXY.getX() - startXY.getX());
            float y = startXY.getY() + fraction 
                    * (endXY.getY() - startXY.getY()); 
            return new XYHolder(x, y);
        }
    }

    public class MyAnimationView extends View 
               implements ValueAnimator.AnimatorUpdateListener {

        public final ArrayList<ShapeHolder> balls = 
                new ArrayList<ShapeHolder>();
        ValueAnimator bounceAnim = null;
        ShapeHolder ball = null;
        BallXYHolder ballHolder = null;

        public MyAnimationView(Context context) {
            super(context);
            ball = ShapeHolder.createBall(25, 25);
            ballHolder = new BallXYHolder(ball);
        }

        private void createAnimation() {
            if (bounceAnim == null) {
                XYHolder endXY = new XYHolder(300f, 500f);
                bounceAnim = ObjectAnimator.ofObject(ballHolder
                        , "XY", new XYEvaluator(), endXY);
                bounceAnim.setDuration(1500);
                bounceAnim.addUpdateListener(this);
            }
        }

        public void startAnimation() {
            createAnimation();
            bounceAnim.start();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.save();
            canvas.translate(ball.getX(), ball.getY());
            ball.getShape().draw(canvas);
            canvas.restore();
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }
    }
    
    public class XYHolder {
        private float mX;
        private float mY;
        public XYHolder(float x, float y) {
            mX = x;
            mY = y;
        }
        public float getX() {
            return mX;
        }
        public void setX(float x) {
            mX = x;
        }
        public float getY() {
            return mY;
        }
        public void setY(float y) {
            mY = y;
        }
    }
   
    public class BallXYHolder {
        private ShapeHolder mBall;
        public BallXYHolder(ShapeHolder ball) {
            mBall = ball;
        }
        public void setXY(XYHolder xyHolder) {
            mBall.setX(xyHolder.getX());
            mBall.setY(xyHolder.getY());
        }
        public XYHolder getXY() {
            return new XYHolder(mBall.getX(), mBall.getY());
        }
    }
}