package com.Project4;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.view.*;

public class MainActivity extends Activity {

	// MyGameView를 변수로 만든다
	MyGameView mGameView; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    	mGameView = (MyGameView) findViewById(R.id.mGameView); 
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    	mGameView.PauseGame();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    	mGameView.ResumeGame();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
    	mGameView.PauseGame();
    }
    
	//-------------------------------------
	//  Option Menu
	//-------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "게임종료");
		menu.add(0, 2, 0, "일시정지");
		menu.add(0, 3, 0, "계속진행");
		menu.add(0, 4, 0, "게임초기화");
		return true;
	}

	//-------------------------------------
	//  onOptions ItemSelected
	//-------------------------------------
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case 1:    // 종료
        	mGameView.StopGame();
        	finish();
             break;
        case 2:    // 일시 정지
        	mGameView.PauseGame();
             break;
        case 3:    // 계속 진행
        	mGameView.ResumeGame();
            break;
        case 4:    // 게임 재시작
        	mGameView.RestartGame();
        }
        return true;
	}

}

