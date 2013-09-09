package com.Sliding2;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;

public class MainActivity extends Activity {

	// MyGameView를 변수로 만든다
	MyGameView mGameView; 
	boolean isPause = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mGameView == null)
        	mGameView = (MyGameView) findViewById(R.id.mGameView);

        setContentView(R.layout.main);
    }
    
	//-------------------------------------
	//  Option Menu
	//-------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "Quit Game");
		menu.add(0, 2, 0, "Save Game");
		menu.add(0, 3, 0, "Load Game");
		menu.add(0, 4, 0, "Pause Game");
		menu.add(0, 5, 0, "Thumbnail On");
		menu.add(0, 6, 0, "Swap Background");
		return true;
	}

	//-------------------------------------
	//  onOptions ItemSelected
	//-------------------------------------
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case 1:  		// 종료
        	mGameView.StopGame();
        	finish();
            break;
        case 2:   		// SaveGame
        	MyGameView.isSave = true;
            break;
        case 3:   		// LoadGame
        	MyGameView.isLoad = true;
            break;
        case 4:    		// Pause/Resume
        	isPause = !isPause;
        	if (isPause) {
        		mGameView.PauseGame();
        		item.setTitle("Resume Game");
        	} else {
        		mGameView.ResumeGame();
        		item.setTitle("Pause Game");
        	}
            break;
        case 5:    		// Thumbnail
        	mGameView.isThumb = !mGameView.isThumb;
        	if (mGameView.isThumb == true)
        		item.setTitle("Thumbnail Off");		
        	else
        		item.setTitle("Thumbnail On");
            break;
        case 6:   		// 배경화면 바꿈 
        	mGameView.backGround = 3 - mGameView.backGround; 
        }
        return true;
	}
	
}
