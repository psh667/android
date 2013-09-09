package com.appstudio.android.sample.c26_babyshake;

import com.appstudio.android.sample.c26_rolypoly.R;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.MotionEvent;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class BabyShakeActivity extends Activity implements AnimatorListener,
        SensorEventListener {
    private static final int MAX_DURATION = 5000;
    private static final int LEFT_DEGREE = -75;
    private static final int RIGHT_DEGREE = 75;
    private static final float DECREASE_RATIO = 0.8f;
    private static final float MINIMUM_DEGREE = 0.01f;
    private static final float MINIMUM_ACCEL = 5;
    private static final float ACCEL_TO_DEGREE = 5;
    private static final float ON_GOING_DEGREE = 10;
    private float mTargetDegree;
    private SensorManager mSensorManager;
    private ImageView santaView;
    private ViewPropertyAnimator mAnimator;
    private SoundPool mSoundPool;
    private int mSoundId;
    private int mMaxVolume;
    private AudioManager mAudioManager;
    private WakeLock mWakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.babyshake_activity);
        santaView = (ImageView) findViewById(R.id.santa);
        mAnimator = santaView.animate();
        mAnimator.setListener(this);
        mTargetDegree = 0;
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        mSoundId = mSoundPool.load(this, R.raw.bell, 1);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
                "appstudio");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager
                .getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                SensorManager.SENSOR_DELAY_GAME);

    }

    private void myAnimate(float targetDegree) {
        float currentDegree = santaView.getRotation();
        santaView.setPivotY(santaView.getHeight() - 10);
        santaView.setPivotX(santaView.getWidth() / 2);
        mAnimator.rotation(targetDegree);
        long duration = (long) ((Math.abs(targetDegree - currentDegree) / (RIGHT_DEGREE - LEFT_DEGREE)) * MAX_DURATION);
        mAnimator.setDuration(duration);

        if (Math.abs(currentDegree) > Math.abs(targetDegree)) {
            mAnimator.setInterpolator(new AccelerateInterpolator(1));
        } else {
            mAnimator.setInterpolator(new DecelerateInterpolator(1));
        }
        mWakeLock.setReferenceCounted(false);
        mWakeLock.acquire();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int streamVolume = mAudioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            float volume = (float)streamVolume / (float)mMaxVolume;
            mSoundPool.play(mSoundId, volume, volume, 1, 0, 1.0f);
        }
        return true;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        float currentDegree = santaView.getRotation();
        if (currentDegree == 0.0f) {
            if (mTargetDegree == 0.0f) {
                mWakeLock.release();
                return;
            }
            float targetDegree = mTargetDegree * DECREASE_RATIO;
            if (Math.abs(targetDegree) <= MINIMUM_DEGREE) {
                
                targetDegree = 0.0f;
            }
            mTargetDegree = -targetDegree;
            myAnimate(mTargetDegree);
        } else {
            myAnimate(0.0f);
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent arg0) {

        if (Math.abs(mTargetDegree) > ON_GOING_DEGREE)
            return;
        float x = arg0.values[0];
        if (Math.abs(x) < MINIMUM_ACCEL)
            return;
        int degree = (int) (x * ACCEL_TO_DEGREE);
        mTargetDegree = mTargetDegree + degree;
        if (mTargetDegree < LEFT_DEGREE) {
            mTargetDegree = LEFT_DEGREE;
        }

        if (mTargetDegree > RIGHT_DEGREE) {
            mTargetDegree = RIGHT_DEGREE;
        }
        myAnimate(mTargetDegree);
    }
}