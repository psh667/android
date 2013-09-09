package com.appstudio.android.sample.ch_24_5;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SpeechActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_activity);
    }
    
    public void mOnClickText2Speech(View v)  {
	startActivityForResult(new Intent(this, Text2SpeechActivity.class), 0);
    }
    
    public void mOnClickSpeech2Text(View v)  {
	startActivityForResult(new Intent(this, Speech2TextActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
    }
}