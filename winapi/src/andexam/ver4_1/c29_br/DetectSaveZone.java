package andexam.ver4_1.c29_br;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class DetectSaveZone extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detectsavezone);
	}
	
	public void mOnClick(View v) {
		v.postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent();
				intent.setAction("andexam.ver4_1.SAVEZONE");
				sendBroadcast(intent);
			}
		}, 10000);
	}	
}