package com.appstudio.android.sample.ch_23_3;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PhoneCallIncommingBugActivity extends Activity {
    private MediaPlayer mPlayer;
    private Button mPlayButton;
    private final String PLAY_START_MSG = "Play Start";
    private final String PLAY_STOP_MSG = "Play Pause";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonecallincomming_bug_activity);
        mPlayer = MediaPlayer.create(this, R.raw.music);
        mPlayButton = (Button)findViewById(R.id.play);
        mPlayButton.setText(PLAY_START_MSG);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }

	public void mPlayAndStop(View view)  {
	    if(mPlayer.isPlaying())  {
	        mPlayer.pause();
	        mPlayButton.setText(PLAY_START_MSG);
	    }else {
	        mPlayer.start();
	        mPlayButton.setText(PLAY_STOP_MSG);
	    }
    }
}