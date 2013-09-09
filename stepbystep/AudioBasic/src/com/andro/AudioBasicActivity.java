package com.andro;

import android.app.Activity;
import android.os.Bundle;
import android.media.MediaPlayer;

public class AudioBasicActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // drawable 폴더에 있는 bibidibabidibuu.mp3 파일 인식
        MediaPlayer mp = MediaPlayer.create(this,R.drawable.bibidibabidibuu);
        // 오디오 파일의 실행 시 반복 여부 지정(true: 반복, false: 반복 않음)
        mp.setLooping(false); 
        // 오디오 실행 
        mp.start();
    }
}