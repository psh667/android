package org.nashorn.exam0405;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Exam0405 extends Activity {
    /** Called when the activity is first created. */
	MediaPlayer player = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button soundButton = (Button)findViewById(R.id.sound);
        soundButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if (player != null)
					player.release();
				
				player = MediaPlayer.create(Exam0405.this, R.raw.a01);
				
				try
				{
					player.start();	
				} catch(Exception e)
				{
					Toast.makeText(Exam0405.this, e.toString(), Toast.LENGTH_LONG).show();
				}
			}
		});
        
        Button configButton = (Button)findViewById(R.id.config);
        configButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(Exam0405.this, MyPreference.class);
				startActivity(i);
			}
		});
        
        
    }
    
    //@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
        return true;
    }
	
    //@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }
    
    //@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	   		case R.id.menu01:
	   		{
	   			Toast.makeText(this, "메뉴1을 선택했습니다.", Toast.LENGTH_SHORT).show();
	   		}
   			return true;
	   			
	   		case R.id.menu02:
   			{
 				Toast.makeText(this, "메뉴2를 선택했습니다.", Toast.LENGTH_SHORT).show();
   			}
   			return true;
	   			
	   		case R.id.menu03:
   			{
   				Toast.makeText(this, "메뉴3을 선택했습니다.", Toast.LENGTH_SHORT).show();
   			}
   			return true;
   			
	   		case R.id.menu04:
   			{
   				Toast.makeText(this, "메뉴4를 선택했습니다.", Toast.LENGTH_SHORT).show();
   			}
   			return true;
    	}
	    
    	return super.onOptionsItemSelected(item);
    }
}