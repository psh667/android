package com.StarWars;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.view.*;

public class MainActivity extends Activity {

	static MyGameView mGameView; 
	boolean isPaused = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);
        
    	mGameView = (MyGameView) findViewById(R.id.mGameView); 
    }
    
	//-------------------------------------
	//  Option Menu
	//-------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "Quit Game");
		menu.add(0, 2, 0, "Pause Game");
		menu.add(0, 3, 0, "Auto Fire On");
		menu.add(0, 4, 0, "Music Off");
		menu.add(0, 5, 0, "Sound Off");
		menu.add(0, 6, 0, "Vibrator Off");
		return true;
	}

	//-------------------------------------
	//  onOptions ItemSelected
	//-------------------------------------
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case 1:  		// QuitGame
        	MyGameView.StopGame();
			startActivity(new Intent(MainActivity.this, StartGame.class));
        	finish();
             break;
        case 2:   		// PauseGame
        	isPaused = !isPaused;
        	if (isPaused) {
        		MyGameView.PauseGame();
        		item.setTitle("Resume Game");
        	} else {
        		MyGameView.ResumeGame();
        		item.setTitle("Pause Game");
        	}	
             break;
        case 3:   		// Auto Fire On/off
        	MyGameView.isAutoFire = !MyGameView.isAutoFire;
        	if (MyGameView.isAutoFire) {
        		item.setTitle("Auto Fire Off");
        	}
        	else {
        		item.setTitle("Auto Fire On");
        	}
            break;
        case 4:   		// Music On/off
        	MyGameView.isMusic = !MyGameView.isMusic;
        	if (MyGameView.isMusic) {
        		MyGameView.player.start();
        		item.setTitle("Music Off");
        	}
        	else {
        		MyGameView.player.stop();
        		item.setTitle("Music On");
        	}
            break;
        case 5:    		// Sound On/Off
        	MyGameView.isSound = !MyGameView.isSound;
        	if (MyGameView.isSound) {
        		item.setTitle("Sound Off");
        	}
        	else {
        		item.setTitle("Sound On");
        	}
            break;
        case 6:    		// Vibrator On/Off
        	MyGameView.isVibe = !MyGameView.isVibe;
        	if (MyGameView.isVibe) {
        		item.setTitle("Vibrator Off");
        	}
        	else {
        		item.setTitle("Vibrator On");
        	}
        }
        return true;
	}
	
}

