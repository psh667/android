package com.beanie.example;

import com.beanie.example.views.MyProgressBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProgressBarTest extends Activity {
	private MyProgressBar pBar;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        pBar = (MyProgressBar)findViewById(R.id.progressBar);
        
        Button start = (Button)findViewById(R.id.start);
        Button stop = (Button)findViewById(R.id.stop);
        
        start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				start();
			}
		});
        
        stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stop();
			}
		});
    }
    
    private void start(){
    	pBar.startAnimation();
    }
    
    private void stop(){
    	pBar.dismiss();
    }
}