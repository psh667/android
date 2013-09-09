package com.appstudio.android.sample.ch_24_5;

import java.util.Locale;
import java.util.Random;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Text2SpeechActivity extends Activity  
                       implements TextToSpeech.OnInitListener {
    private static final String TAG = "TextToSpeech";
    private TextToSpeech mTts;
    private Button mPlayButton;
    private EditText mText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text2speech_activity);
        mTts = new TextToSpeech(this, this);
        mPlayButton = (Button) findViewById(R.id.play);
        mText = (EditText) findViewById(R.id.text);
        mPlayButton.setOnClickListener(
                                   new View.OnClickListener() {
            public void onClick(View v) {
                sayIt();
            }
        });
    }
    @Override
    public void onDestroy() {
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }
    // TextToSpeech 초기화 완료 시 콜백 호출
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA 
                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            } else {
                mPlayButton.setEnabled(true);
            }
        } else {
            Log.e(TAG, "TextToSpeech 초기화 에러!");
        }
    }

    private void sayIt() {
        String it = mText.getText().toString();
        mTts.speak(it, TextToSpeech.QUEUE_FLUSH, null);
    }
}
