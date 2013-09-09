package com.appstudio.android.sample.ch_6;

import com.appstudio.android.sample.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

public class OnClickActivity extends Activity implements OnClickListener{
	static final int[] BUTTONS = 
		{
			R.id.button1,
			R.id.button2,
			R.id.button3
		};
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.onclickmain);
	    
		OnClickListener radio_listener = new OnClickListener() {
		    public void onClick(View v) {
		        RadioButton rb = (RadioButton) v;
		        Toast.makeText(OnClickActivity.this, rb.getText() + " is Selected", Toast.LENGTH_SHORT).show();
		    }
		}; 
		
		final CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
		checkbox1.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        if (((CheckBox) v).isChecked()) {
		            Toast.makeText(OnClickActivity.this, ((CheckBox) v).getText() +  " is Selected", Toast.LENGTH_SHORT).show();
		        } else {
		            Toast.makeText(OnClickActivity.this, ((CheckBox) v).getText() +  " is Not selected", Toast.LENGTH_SHORT).show();
		        }
		    }
		});
		final CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
		checkbox2.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        if (((CheckBox) v).isChecked()) {
		            Toast.makeText(OnClickActivity.this, ((CheckBox) v).getText() +  " is Selected", Toast.LENGTH_SHORT).show();
		        } else {
		            Toast.makeText(OnClickActivity.this, ((CheckBox) v).getText() +  " is Not selected", Toast.LENGTH_SHORT).show();
		        }
		    }
		});
		
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	Toast.makeText(OnClickActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
		    	Log.i("Button Event","is Clicked");
		    }
		});
		button.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(OnClickActivity.this, "Button Touched", Toast.LENGTH_SHORT).show();
				Log.i("Button Event","is Touched");
				return false;
			}
		});
		for(int btnId:BUTTONS){
			Button tmpButton = (Button) findViewById(btnId);
			tmpButton.setOnClickListener(this);
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.button1:
			Toast.makeText(OnClickActivity.this, "Button id is button1", Toast.LENGTH_SHORT).show();
			break;
		case R.id.button2:
			Toast.makeText(OnClickActivity.this, "Button id is button2", Toast.LENGTH_SHORT).show();
			break;
		case R.id.button3:
			Toast.makeText(OnClickActivity.this, "Button id is button3", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
