
//-------------------------------------
// Game Start - Game Menu
//-------------------------------------
package com.StartGame;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class StartGame extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        
        findViewById(R.id.ImageView01).setOnClickListener(ButtonClick);
        findViewById(R.id.ImageView02).setOnClickListener(ButtonClick);
        findViewById(R.id.ImageView03).setOnClickListener(ButtonClick);
        findViewById(R.id.ImageView04).setOnClickListener(ButtonClick);
        findViewById(R.id.ImageView05).setOnClickListener(ButtonClick);
        findViewById(R.id.ImageView06).setOnClickListener(ButtonClick);
    }
    
    //------------------------------------
    // Button Click
    //------------------------------------
    Button.OnClickListener ButtonClick = new Button.OnClickListener() {
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.ImageView01: // New Game
				((GlobalVars) getApplicationContext()).setLoad(false);
				startActivity(new Intent(StartGame.this, MainActivity.class));
				break;
			case R.id.ImageView02: // Load Game
	    		((GlobalVars) getApplicationContext()).setLoad(true);
				startActivityForResult(new Intent(StartGame.this, MainActivity.class), 1);
				break;
			case R.id.ImageView03: // Options Game
				startActivity(new Intent(StartGame.this, Options.class));
				break;
			case R.id.ImageView04: // Help
				startActivity(new Intent(StartGame.this, Help.class));
				break;
			case R.id.ImageView05: // About
				startActivity(new Intent(StartGame.this, About.class));
				break;
			case R.id.ImageView06: // Quit	
				finish();
			}
		}
    };
    
}