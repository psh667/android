package com.appstudio.android.sample.ch_6;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OnKeyActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.onkeymain);
	    
	    final EditText edittext = (EditText) findViewById(R.id.editText1);
	    final TextView resultView = (TextView) findViewById(R.id.editResult1);
	    
	    edittext.setOnKeyListener(new OnKeyListener() {
	    	public boolean onKey(View v, int keyCode, KeyEvent event) {
	            // If the event is a key-down event on the "enter" button
	            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
	                (keyCode == KeyEvent.KEYCODE_ENTER)) {
	              // Perform action on key press
	              Toast.makeText(OnKeyActivity.this, edittext.getText(), Toast.LENGTH_SHORT).show();
	              // Hide KeyBoard
	              InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	              imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
	              return true;
	            }
	            return false;
	        }
	    });	
	}
}
