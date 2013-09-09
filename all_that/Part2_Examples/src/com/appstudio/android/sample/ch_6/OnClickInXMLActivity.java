package com.appstudio.android.sample.ch_6;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class OnClickInXMLActivity  extends Activity {

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.onclickinxmlmain);
	}
	
	public void myClickHandler(View v) {
		switch(v.getId()){
		case R.id.button1:
			Toast.makeText(OnClickInXMLActivity.this, "Button id is button1", Toast.LENGTH_SHORT).show();
			break;
		case R.id.button2:
			Toast.makeText(OnClickInXMLActivity.this, "Button id is button2", Toast.LENGTH_SHORT).show();
			break;
		case R.id.button3:
			Toast.makeText(OnClickInXMLActivity.this, "Button id is button3", Toast.LENGTH_SHORT).show();
			break;
		}
    }

}
