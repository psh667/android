/*
 * Copyright (C) 2009 The Android Open Source Project
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

package com.appstudio.android.sample.ch_24_2;

import java.io.File;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MediaPlayerAudioActivity extends Activity {

    private static final String TAG = "MediaPlayerDemo";
    private MediaPlayer mMediaPlayer;
    private static final String MEDIA = "media";
    private static final int LOCAL_AUDIO = 1;
    private static final int RESOURCES_AUDIO = 3;
    private String path;
    private Button mButton;
    private SeekBar mSeekBar;
    private final Handler handler = new Handler();
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.media_player_audio_activity);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar1);
        mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick();
            }
        });
        mSeekBar.setOnTouchListener(new OnTouchListener()  {
            @Override 
            public boolean onTouch(View v, MotionEvent event) {
                seekChange(v);
                return false; 
            }
        });
    }
    
    public void startPlayProgressUpdater() {
        mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
        if (mMediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification,1000);
        }else{
            mMediaPlayer.pause();
            mButton.setText("Play");
            mSeekBar.setProgress(0);
        }
    }
    
    // 시크바에 따른 재생 위치 이동
    private void seekChange(View v)  {
        if(mMediaPlayer.isPlaying()){
            SeekBar sb = (SeekBar)v;
            mMediaPlayer.seekTo(sb.getProgress());
        }
    }
    
    private void playAudio(Integer media) {
        try {
            switch (media) {
            case LOCAL_AUDIO:
                path = "";
                String sdPath = Environment
                    .getExternalStorageDirectory()
                    .getAbsolutePath();
              //path = sdPath + File.separator + "music.mp3";
                Log.d("file", path);
                if (path == "") {
                    Toast.makeText(
                        MediaPlayerAudioActivity.this
                        ,"파일 위치를 지정하도록 액티비"
                        +"티를 수정하세요."
                        ,Toast.LENGTH_LONG).show();
                }
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(path);
                mMediaPlayer.prepare();
                mSeekBar.setMax(mMediaPlayer.getDuration());
                mMediaPlayer.start();
                break;
            case RESOURCES_AUDIO:
                mMediaPlayer = 
                    MediaPlayer.create(this, R.raw.music);
                mSeekBar.setMax(mMediaPlayer.getDuration());
                mMediaPlayer.start();
            }
        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
    
    private void buttonClick(){
        if ("Play".equals(mButton.getText().toString())) {
            mButton.setText("Stop");
            try {
                Bundle extras = getIntent().getExtras();
                playAudio(extras.getInt(MEDIA));
                startPlayProgressUpdater();
            }catch (IllegalStateException e) {
                mMediaPlayer.pause();
            }
        } else {
            mButton.setText("Play");
            mMediaPlayer.pause();
        }
    }
}
