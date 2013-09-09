package com.andro;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AudioAdvLabActivity extends Activity implements OnClickListener {
	// 미디어 플레이어 배열 선언
	MediaPlayer[] mp = new MediaPlayer[2];
	// 텍스트뷰 배열 선언
	TextView[]    tv = new TextView[2];
 
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ///// 미디어 플레이어 초기화
        for(int i=0; i<tv.length; i++) {
            // drawable 폴더에 있는 오디오 파일을 읽음
        	if (i == 0)
    	        mp[i] = MediaPlayer.create(this, R.drawable.bibidibabidibuu);
        	else if (i == 1)
    	        mp[i] = MediaPlayer.create(this, R.drawable.lovemetender);
            // 오디오 파일의 실행 시 반복 여부 지정(미반복)
            mp[0].setLooping(false); 
        }
        
        // main.xml의 리니어레이아웃 인식 
        LinearLayout layout = (LinearLayout)findViewById(R.id.album);
        
        ///// 오디오 목록 출력
        for(int i=0; i<tv.length; i++) {
	        // 텍스트뷰 생성 
	        tv[i] = new TextView(this);
	        // 텍스트뷰에 id 부여
	        tv[i].setId(i);
	        // 텍스트뷰에 문자 출력 
	        if (i == 0)
	            tv[i].append("Bibidi Babidi Buu");
	        else if (i == 1)
	            tv[i].append("Love Me Tender");
	        	
	        // 텍스트뷰1의 문자 크기 지정 
	        tv[i].setTextSize(20);
	        // 텍스트뷰의 문자색 지정
	        tv[i].setTextColor(Color.YELLOW);
	        // 텍스트뷰에 클릭 대기 
	        tv[i].setOnClickListener(this);        
	        // 텍스트뷰을 리니어레이아웃에 추가 
	        layout.addView(tv[i]);
        }
    }
    
	// 텍스트뷰가 클릭되면 실행
	public void onClick(View v) {
		// 클릭된 텍스트뷰의 id 인식
		int tvId = v.getId();
		
		// 오디오 곡명 클릭여부에 따른 목록 및 재생/중지 설정  
		for (int i=0; i<tv.length; i++) {
	    	// i번째 텍스트뷰가 클릭되었을 때
	        if(i == tvId) {
	            // 오디오가 실행 중이면 중지
	            if(mp[i].isPlaying()) {
	                mp[i].pause();
	                tv[i].setTextColor(Color.YELLOW);
	                tv[i].setBackgroundColor(Color.BLACK);
		        // 오디오가 중지된 상태이면 실행
	            } else {
	                mp[i].start();
	                tv[i].setTextColor(Color.BLACK);
	         	    tv[i].setBackgroundColor(Color.YELLOW);
	            }
		    // i번째 텍스트뷰가 클릭되지 않은 경우
	        } else {
	            // 오디오가 실행 중이면 중지
	        	if(mp[i].isPlaying()) {
	        		mp[i].pause();
	                tv[i].setTextColor(Color.YELLOW);
	                tv[i].setBackgroundColor(Color.BLACK);
	        	} 
	        }  
		} 
    }
}