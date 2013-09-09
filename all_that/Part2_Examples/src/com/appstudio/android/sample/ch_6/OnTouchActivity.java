package com.appstudio.android.sample.ch_6;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;

public class OnTouchActivity extends Activity implements OnTouchListener {
	private final int START_DRAG = 0;
	private final int END_DRAG = 1;
	private int isMoving;
	private float offset_x, offset_y;
	private boolean start_yn = true;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ontouchmain);
		Button button = (Button)this.findViewById(R.id.button1);
		button.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(start_yn){
				offset_x = event.getRawX();
				offset_y = event.getRawY();
				start_yn = false;
			}
            Toast.makeText(OnTouchActivity.this, "Drag Start", Toast.LENGTH_SHORT).show();
            isMoving = START_DRAG;
		}else if(event.getAction() == MotionEvent.ACTION_UP){
            Toast.makeText(OnTouchActivity.this, "Drag End", Toast.LENGTH_SHORT).show();
            isMoving = END_DRAG;
		}else if(event.getAction() == MotionEvent.ACTION_MOVE){
			if(isMoving == START_DRAG){
				v.setX((int)event.getRawX() - offset_x);
				v.setY((int)event.getRawY() - offset_y);
			}
		}
		return false;
	}
}
