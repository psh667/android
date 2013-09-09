package andexam.ver4_1.c17_activity;

import andexam.ver4_1.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActParent extends Activity {
	static final String TAG = "ActParent"; 

	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actparent);
	}
	
	public void mOnClick(View v) {
		Log.i(TAG, "startActivity");
		Intent intent = new Intent(this, ActChild.class);
		startActivity(intent);
	}
	
	public void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
	}

	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
	}

	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
	}

	public void onRestart() {
		super.onRestart();
		Log.i(TAG, "onRestart");
	}

	public void onStop() {
		super.onStop();
		Log.i(TAG, "onStop");
	}

	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}
}