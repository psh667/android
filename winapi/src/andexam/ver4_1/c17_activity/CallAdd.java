package andexam.ver4_1.c17_activity;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;

public class CallAdd extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calladd);
	}

	public void mOnClick(View v) {
		Intent intent = new Intent("andexam.ver4_1.ADD");
		intent.putExtra("left", 3);
		intent.putExtra("right", 4);
		startActivity(intent); 
	}
}
