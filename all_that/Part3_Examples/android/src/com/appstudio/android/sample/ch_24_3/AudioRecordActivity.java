package com.appstudio.android.sample.ch_24_3;

import java.io.IOException;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;



public class AudioRecordActivity extends Activity  {
    private static final String LOG_TAG = "AudioRecord";
    private static String mFileName = null;
    private Button mRecordButton = null;
    private MediaRecorder mRecorder = null;
    private boolean mStartRecording = true;
    private Button   mPlayButton = null;
    private MediaPlayer   mPlayer = null;
    private boolean mStartPlaying = true;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_record_activity);
        mRecordButton = (Button)findViewById(R.id.record);
        mRecordButton.setOnClickListener(mRecordClicker);
        mPlayButton = (Button)findViewById(R.id.play);
        mPlayButton.setOnClickListener(mPlayClicker);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
    
    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(
                    mOnCompletionListener);
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayButton.setText("재생 시작");
        mStartPlaying = true;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(
                MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(
                MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(
                MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public AudioRecordActivity() {
        mFileName = Environment.getExternalStorageDirectory()
                               .getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }

    private Button.OnClickListener mRecordClicker = 
                                 new Button.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button)v;
            onRecord(mStartRecording);
            if (mStartRecording) {
                button.setText("녹음 종료");
            } else {
                button.setText("녹음 시작");
            }
            mStartRecording = !mStartRecording;
        }
    };

    private Button.OnClickListener mPlayClicker = 
                                 new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            onPlay(mStartPlaying);
            if (mStartPlaying) {
                button.setText("재생 중");
            } else {
                button.setText("재생 시작");
            }
            mStartPlaying = !mStartPlaying;
        }
    };
        
    private OnCompletionListener mOnCompletionListener = 
                      new MediaPlayer.OnCompletionListener()  {
        @Override
        public void onCompletion(MediaPlayer mp) {
            stopPlaying();
        }
    };
}