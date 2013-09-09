package com.appstudio.android.sample.ch_6;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.Toast;

public class OnLongClickActivity extends Activity {
	boolean isRed = true;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onlongclickmain);
		
		final Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	Toast.makeText(OnLongClickActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
		    }
		});
		
		button.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Button b = (Button)findViewById(v.getId());
				if(isRed){
					isRed = false;
					b.setBackgroundColor(Color.CYAN);
				}else{
					isRed = true;
					b.setBackgroundColor(Color.RED);
				}
				Toast.makeText(OnLongClickActivity.this, "버튼이 롱클릭되었습니다. 그리고 색상이 "+(isRed?"빨간색":"하늘색")+"으로 변경 되었습니다. ", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}
}
