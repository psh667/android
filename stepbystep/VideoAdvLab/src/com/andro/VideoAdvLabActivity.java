package com.andro;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoAdvLabActivity extends Activity implements OnClickListener {
	// 비디오 목록 배열 생성
	TextView[] tv = new TextView[5];
	// 비디오 제목 배열 초기화
	String[] title = {"지하철1", "자동차2", "지하철3", "자동차4", "지하철5"};
	// main.xml의 비디오 툴력 영역의 레이아웃을 위한 객체 선언
	LinearLayout layout_videoview;
	// 비디오뷰 객체 선언;
	VideoView videoView;
	// 비디오 리소스 배열 선언
	Uri[] uri = new Uri[5];
	// 비디오 리소스의 파일에 대한 배열   
	int[] raw_uri	= {R.drawable.subway, 
	    		       R.drawable.street,
	    		       R.drawable.subway,
	    		       R.drawable.street,
	    		       R.drawable.subway};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // main.xml의 리니어레이아웃 인식 
        LinearLayout layout_videolist = (LinearLayout)findViewById(R.id.videolist_area);
        // main.xml의 리니어레이아웃 인식 
        layout_videoview = (LinearLayout)findViewById(R.id.videoview_area);
        
        ///// 오디오 목록 출력
        for(int i=0; i<tv.length; i++) {
	        // 텍스트뷰 생성 
	        tv[i] = new TextView(this);
	        // 텍스트뷰에 id 부여
	        tv[i].setId(i);
	        // 텍스트뷰에 문자 출력 
            tv[i].append(title[i]);
	        	
	        // 텍스트뷰1의 문자 크기 지정 
	        tv[i].setTextSize(25);
	        // 텍스트뷰의 문자색 지정
	        tv[i].setTextColor(Color.YELLOW);
	        // 텍스트뷰에 클릭 대기 
	        tv[i].setOnClickListener(this);        
	        // 텍스트뷰을 리니어레이아웃에 추가 
	        layout_videolist.addView(tv[i]);
	        
	        // 비디오 리소스 인식
            uri[i] = Uri.parse("android.resource://com.andro/" + raw_uri[i]);
        }
        
        // VideoView의 객체 생성
        videoView = new VideoView(this);
        // VideoView 객체를 LinearLayout에 추가 
        layout_videoview.addView(videoView);
        
        // 미디어 제어기 초기화
        MediaController mediaController = new MediaController(this);
        
        // 비디오뷰에 미디어 제어기 설치 
        videoView.setMediaController(mediaController);
    }
 
    public void onClick(View v) {
   
		// 클릭된 텍스트뷰의 id 인식
		int tvId = v.getId();
		
		for(int i=0; i<tv.length; i++) { 
			
			if (i == tvId) {
		        // 텍스트뷰의 문자색 지정
		        tv[i].setTextColor(Color.BLUE);
		        // 텍스트뷰의 문자색 지정
		        tv[i].setBackgroundColor(Color.YELLOW);
			} else {
		        // 텍스트뷰의 문자색 지정
		        tv[i].setTextColor(Color.YELLOW);
		        // 텍스트뷰의 문자색 지정
		        tv[i].setBackgroundColor(Color.BLUE);
			}
		}
        
        // 비디오뷰에 비디오 리소스 설정
        videoView.setVideoURI(uri[tvId]);
        
        // 비디오 재생 시작 
        videoView.start();
    }    
}