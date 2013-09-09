package com.andro;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoBasicActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ///// main.xml 레이아웃에 있는 비디오뷰 인식
        VideoView videoView = (VideoView) findViewById(R.id.videoview_area);
        
        // 미디어 제어기 초기화
        MediaController mediaController = new MediaController(this);
        
        // 비디오뷰에 미디어 제어기 설치 
        videoView.setMediaController(mediaController);

        // drawable 폴더에 있는 비디오 리소스(subway.3gp) 인식
        Uri raw_uri = Uri.parse("android.resource://com.andro/" + R.drawable.subway);
        
        // 비디오뷰에 리소스 설정
        videoView.setVideoURI(raw_uri);
        
        // 비디오 재생 시작 
        videoView.start();        
    }
}