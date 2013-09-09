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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.appstudio.android.sample.R;

public class LayoutAnimationsHideShowActivity extends Activity {
    static String TAG = "appstudio";
    ViewGroup container = null;
    private LayoutTransition mTransitioner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.layout_animations_hideshow_activity);
        final CheckBox hideGoneCB = 
                (CheckBox) findViewById(R.id.hideGoneCB);
        container = new LinearLayout(this);
        container.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        for (int i = 0; i < 4; ++i) {
            Button newButton = new Button(this);
            newButton.setText(String.valueOf(i));
            container.addView(newButton);
            newButton.setOnClickListener(
                                    new View.OnClickListener() {
                public void onClick(View v) {
                    v.setVisibility(hideGoneCB.isChecked() 
                            ? View.GONE : View.INVISIBLE);
                }
            });
        }
        resetTransition();
        ViewGroup parent = (ViewGroup) findViewById(R.id.parent);
        parent.addView(container);
        Button addButton = 
                (Button) findViewById(R.id.addNewButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                for (int i = 0; 
                        i < container.getChildCount(); ++i) {
                    View view = (View) container.getChildAt(i);
                    view.setVisibility(View.VISIBLE);
                }
            }
        });

        CheckBox customAnimCB = 
                (CheckBox) findViewById(R.id.customAnimCB);
        customAnimCB.setOnCheckedChangeListener(
                  new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(
                    CompoundButton buttonView
                    , boolean isChecked) {
                long duration;
                if (isChecked) {
                    mTransitioner.setStagger(LayoutTransition
                            .CHANGE_APPEARING, 30);
                    mTransitioner.setStagger(LayoutTransition
                            .CHANGE_DISAPPEARING, 30);
                    setupCustomAnimations();
                    duration = 500;
                } else {
                    resetTransition();
                    duration = 300;
                }
                mTransitioner.setDuration(duration);
            }
        });
    }

    private void setupCustomAnimations() {
        // Changing while Adding
        PropertyValuesHolder pvhLeft =
                PropertyValuesHolder.ofInt("left", 0, 1);
        PropertyValuesHolder pvhTop =
                PropertyValuesHolder.ofInt("top", 0, 1);
        PropertyValuesHolder pvhRight =
                PropertyValuesHolder.ofInt("right", 0, 1);
        PropertyValuesHolder pvhBottom =
                PropertyValuesHolder.ofInt("bottom", 0, 1);
        PropertyValuesHolder pvhScaleX =
                PropertyValuesHolder.ofFloat("scaleX",1f,0f,1f);
        PropertyValuesHolder pvhScaleY =
                PropertyValuesHolder.ofFloat("scaleY",1f,0f,1f);
        final ObjectAnimator changeIn = 
                ObjectAnimator.ofPropertyValuesHolder(
                        this, pvhLeft, pvhTop, pvhRight
                        , pvhBottom, pvhScaleX, pvhScaleY);
        long duration = mTransitioner.getDuration(
                LayoutTransition.CHANGE_APPEARING);
        changeIn.setDuration(duration);
        mTransitioner.setAnimator(
                LayoutTransition.CHANGE_APPEARING, changeIn);

        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.9999f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder
                .ofKeyframe("rotation", kf0, kf1, kf2);
        final ObjectAnimator changeOut = ObjectAnimator
                .ofPropertyValuesHolder(this, pvhLeft, pvhTop
                        , pvhRight, pvhBottom, pvhRotation);
        duration = mTransitioner.getDuration(
                LayoutTransition.CHANGE_DISAPPEARING);
        changeOut.setDuration(duration);
        mTransitioner.setAnimator(
                LayoutTransition.CHANGE_DISAPPEARING, changeOut);

        ObjectAnimator animIn = 
                ObjectAnimator.ofFloat(null,"rotationY",90f,0f);
        duration = mTransitioner.getDuration(
                LayoutTransition.APPEARING);
        animIn.setDuration(duration);
        mTransitioner.setAnimator(
                LayoutTransition.APPEARING, animIn);

        ObjectAnimator animOut = 
                ObjectAnimator.ofFloat(null,"rotationX",0f,90f);
        duration = mTransitioner.getDuration(
                LayoutTransition.DISAPPEARING);
        animOut.setDuration(duration);
        mTransitioner.setAnimator(
                LayoutTransition.DISAPPEARING, animOut);
        
        animOut.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = 
                       (View)((ObjectAnimator)anim).getTarget();
                view.setRotationX(0f);
            }
        });
    }

    private void resetTransition() {
        mTransitioner = new LayoutTransition();
        container.setLayoutTransition(mTransitioner);
    }
}