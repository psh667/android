package com.andro;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AudioLabActivity extends Activity implements OnClickListener {
	MediaPlayer mp;
	TextView    tv; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // drawable 폴더에 있는 bibidibabidibuu.mp3 파일 인식
        mp = MediaPlayer.create(this, R.drawable.bibidibabidibuu);
        // 오디오 파일의 실행 시 반복 여부 지정(true: 반복, false: 반복 않음)
        mp.setLooping(false); 
        
        ///// main.xml의 TextView 리소스의 클릭 대기: 시작 
        // TextView 리소스(ID가 play_music1)를 인식함
        tv = (TextView) this.findViewById(R.id.play_music1);
        // TextView 리소스의 클릭 대기, 클릭되면 onClick 메소드가 실행됨
        tv.setOnClickListener(this);
        ///// main.xml의 TextView 리소스의 클릭 대기: 시작 
    }
    
    // TextView 리소스(ID가 play_music1)가 클릭되면 실행됨
	public void onClick(View v) {
		///// 오디오 실행: 시작
		// 오디오가 실행 중인 경우
        if(mp.isPlaying()) {
        	// 오디오 실행 중지
            mp.pause();
            // 문자색: 흰색
            tv.setTextColor(Color.WHITE);
            // 문자 배경색: 검은색 
            tv.setBackgroundColor(Color.BLACK);
    	// 오디오가 실행이 중지 상태인 경우
        } else {
        	// 오디오 실행 
            mp.start();
            // 문자색: 검은색
            tv.setTextColor(Color.BLACK);
            // 문자배경색: 노란색
            tv.setBackgroundColor(Color.YELLOW);
        }
        ///// 오디오 실행: 끝
    }
}