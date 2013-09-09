package andexam.ver4_1.c07_output;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;

public class Vibrate extends Activity {
	Vibrator mVib;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vibrate);
		
		mVib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
	}

	protected void onDestroy() {
		super.onDestroy();
		mVib.cancel();
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnvibrate1:
			mVib.vibrate(500);
			break;
		case R.id.btnvibrate2:
			mVib.vibrate(new long[] {100, 50 , 200, 50 }, 0);
			break;
		case R.id.btnvibratestop:
			mVib.cancel();
			break;
		}
	}
}
