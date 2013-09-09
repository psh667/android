package com.Sliding1;

import android.app.*;
import android.os.*;
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
    
	//-------------------------------------
	//  Option Menu
	//-------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "Quit Game");
		menu.add(0, 2, 0, "Save Game");
		menu.add(0, 3, 0, "Load Game");
		menu.add(0, 4, 0, "Pause Game");
		menu.add(0, 5, 0, "Animation Off");
		menu.add(0, 6, 0, "Thumbnail On");
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
        case 2:   		 // SaveGame
             break;
        case 3:   		 // LoadGame
            break;
        case 4:    		// Pause/Resume
            break;
        case 5:    		// Animation
            break;
        case 6:   		 // Thumbnail
        }
        return true;
	}
    
}

