package com.appstudio.android.sample.ch_24_2;


import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MediaPlayerActivity extends Activity {
    private Button mlocalvideo;
    private Button mstreamvideo;
    private Button mlocalaudio;
    private Button mresourcesaudio;
    private static final String MEDIA = "media";
    private static final int LOCAL_AUDIO = 1;
    private static final int RESOURCES_AUDIO = 3;
    private static final int LOCAL_VIDEO = 4;
    private static final int STREAM_VIDEO = 5;
    @Override
    protected void onCreate(Bundle icicle) {
        // TODO Auto-generated method stub
        super.onCreate(icicle);
        setContentView(R.layout.media_player_activity);
        mlocalaudio = (Button) findViewById(R.id.localaudio);
        mlocalaudio.setOnClickListener(mLocalAudioListener);
        mresourcesaudio = (Button) findViewById(R.id.resourcesaudio);
        mresourcesaudio.setOnClickListener(mResourcesAudioListener);

        mlocalvideo = (Button) findViewById(R.id.localvideo);
        mlocalvideo.setOnClickListener(mLocalVideoListener);
        mstreamvideo = (Button) findViewById(R.id.streamvideo);
        mstreamvideo.setOnClickListener(mStreamVideoListener);
    }

    private OnClickListener mLocalAudioListener = new OnClickListener() {
        public void onClick(View v) {
            Intent intent =
                    new Intent(MediaPlayerActivity.this.getApplication(),
                            MediaPlayerAudioActivity.class);
            intent.putExtra(MEDIA, LOCAL_AUDIO);
            startActivity(intent);

        }
    };
    private OnClickListener mResourcesAudioListener = new OnClickListener() {
        public void onClick(View v) {
            Intent intent =
                    new Intent(MediaPlayerActivity.this.getApplication(),
                            MediaPlayerAudioActivity.class);
            intent.putExtra(MEDIA, RESOURCES_AUDIO);
            startActivity(intent);

        }
    };

    private OnClickListener mLocalVideoListener = new OnClickListener() {
        public void onClick(View v) {
            Intent intent =
                    new Intent(MediaPlayerActivity.this,
                            MediaPlayerVideoActivity.class);
            intent.putExtra(MEDIA, LOCAL_VIDEO);
            startActivity(intent);

        }
    };
    private OnClickListener mStreamVideoListener = new OnClickListener() {
        public void onClick(View v) {
            Intent intent =
                    new Intent(MediaPlayerActivity.this,
                            MediaPlayerVideoActivity.class);
            intent.putExtra(MEDIA, STREAM_VIDEO);
            startActivity(intent);

        }
    };



}
