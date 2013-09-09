package com.StarWars;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class StartGame extends Activity {
	
	MediaPlayer mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.startgame);
       
        mPlayer = MediaPlayer.create(this, R.raw.rondo);   	// 파일 읽기 green은 파일명
  	  	mPlayer.setVolume(0.7f, 0.7f); 			      		// 볼륨 설정
  	  	mPlayer.setLooping(true);              				// 반복 연주
 	  	mPlayer.start();
                
        findViewById(R.id.imgStart).setOnClickListener(OnMyClick);
        findViewById(R.id.imgQuit).setOnClickListener(OnMyClick);
        findViewById(R.id.imgOpts).setOnClickListener(OnMyClick);
        findViewById(R.id.imgHelp).setOnClickListener(OnMyClick);
        findViewById(R.id.imgAbout).setOnClickListener(OnMyClick);
    }

    //---------------------------------
    // OnClick Listener
    //---------------------------------
    Button.OnClickListener OnMyClick = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imgStart:
				startActivity(new Intent(StartGame.this, MainActivity.class));
		  	  	mPlayer.stop();
		  	  	finish();
				break;
			case R.id.imgQuit:
		  	  	mPlayer.stop();
				finish();
				break;
			case R.id.imgOpts:
				startActivity(new Intent(StartGame.this, Options.class));
				break;
			case R.id.imgHelp:
				startActivity(new Intent(StartGame.this, Help.class));
				break;
			case R.id.imgAbout:
				startActivity(new Intent(StartGame.this, About.class));
			}
		}
    };
    
}

