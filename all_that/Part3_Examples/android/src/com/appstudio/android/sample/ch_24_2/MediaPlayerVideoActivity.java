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

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


public class MediaPlayerVideoActivity extends Activity 
    implements OnBufferingUpdateListener, OnCompletionListener,
               OnPreparedListener, SurfaceHolder.Callback {

    private static final String TAG = "MediaPlayerDemo";
    private MediaPlayer mMediaPlayer;
    private SurfaceView mPreview;
    private SurfaceHolder holder;
    private String path;
    private Bundle extras;
    private static final String MEDIA = "media";
    private static final int LOCAL_VIDEO = 4;
    private static final int STREAM_VIDEO = 5;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.media_player_video_activity);
        mPreview = (SurfaceView) findViewById(R.id.surface);
        holder = mPreview.getHolder();
        holder.addCallback(this);
        extras = getIntent().getExtras();
    }

    private void playVideo(Integer Media) {
        try {
            path = "";
            switch (Media) {
            case LOCAL_VIDEO:
                String sdPath = Environment
                                 .getExternalStorageDirectory()
                                 .getAbsolutePath();
                // 실행 시 변경 필요
                // path = sdPath + File.separator + "play.mp4";
                if (path == "") {
                    Toast.makeText(
                            MediaPlayerVideoActivity.this
                            ,"파일 위치를 지정하도록 액티비"
                            +"티를 수정하세요."
                            ,Toast.LENGTH_LONG).show();
                }
                break;
            case STREAM_VIDEO:
        //        path = 
        //        "http://appstudiozero.appspot.com/movie.avi";
                if (path == "") {
                    Toast.makeText(
                            MediaPlayerVideoActivity.this
                            ,"파일 위치를 지정하도록 액티비"
                            +"티를 수정하세요."
                            ,Toast.LENGTH_LONG).show();
                }
                break;
            }
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.setDisplay(holder);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setAudioStreamType(
                    AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
    }

    @Override
    public void onBufferingUpdate(
            MediaPlayer arg0, int percent) {
    }
    
    @Override
    public void onCompletion(MediaPlayer arg0) {
    }
    
    @Override
    public void onPrepared(MediaPlayer mediaplayer) {
        startVideoPlayback();
    }

    @Override
    public void surfaceChanged(
            SurfaceHolder surfaceholder, int i, int j, int k) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        playVideo(extras.getInt(MEDIA));
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void startVideoPlayback() {
        holder.setFixedSize(mMediaPlayer.getVideoWidth(), 
                mMediaPlayer.getVideoHeight());
        mMediaPlayer.start();
    }
}
