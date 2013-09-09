package com.appstudio.android.sample.ch_24_1;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SoundPoolActivity extends Activity 
                                   implements OnTouchListener {
    private SoundPool mSoundPool;
    private int mSoundID;
    boolean mIsloaded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soundpool_activity);
        View view = findViewById(R.id.textView1);
        view.setOnTouchListener(this);
        // 하드웨어 버튼이 음악 소리를 조정하도록 설정
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // 10까지 사운드 스트림을 가질 수 있는 풀 생성
        mSoundPool = 
            new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        // 사운드 적재 완료시 수행될 리스너 등록
        mSoundPool.setOnLoadCompleteListener(
                                 new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, 
                                    int sampleId, int status) {
                mIsloaded = true;
            }
        });
        // 사운드 적재
        mSoundID = mSoundPool.load(this, R.raw.siren, 1);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            AudioManager audioManager = 
                (AudioManager) getSystemService(AUDIO_SERVICE);
            // 현재 설정된 실제 볼륨을 가져와서 그에 비례하여 출력
            float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = actualVolume / maxVolume;
            // 사운드가 적재되어 있다면 재생
            if (mIsloaded) {
                mSoundPool.play(
                        mSoundID, volume, volume, 1, 0, 1f);
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
         super.onDestroy();
         mSoundPool.release();
         mSoundPool = null;
    }
}
