package com.appstudio.android.sample.ch_23_3;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;

public class PhoneCallIncommingActivity extends Activity {
    private MediaPlayer mPlayer;
    private boolean mPlayed = false;
    private Button mPlayButton;
    private TelephonyManager mTelephonyManager;
    private final String PLAY_START_MSG = "Play Start";
    private final String PLAY_STOP_MSG = "Play Pause";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonecallincomming_activity);
        mPlayer = MediaPlayer.create(this, R.raw.music);
        mPlayButton = (Button) findViewById(R.id.play);
        mPlayButton.setText(PLAY_START_MSG);

        mTelephonyManager = (TelephonyManager)
                   getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mPhoneStateListener, 
                PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }

    public void mPlayAndStop(View view) {
        if (mPlayed) {
            mPlayer.pause();
            mPlayButton.setText(PLAY_START_MSG);
            mPlayed = false;
        } else {
            mPlayer.start();
            mPlayButton.setText(PLAY_STOP_MSG);
            mPlayed = true;
        }
    }

    private PhoneStateListener mPhoneStateListener = 
        new PhoneStateListener() {
        @Override
        public void onCallStateChanged(
                int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                if (mPlayed) {
                    mPlayer.start();
                }
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                if (mPlayed) {
                    mPlayer.pause();
                }
                break;
            }
        }
    };
}