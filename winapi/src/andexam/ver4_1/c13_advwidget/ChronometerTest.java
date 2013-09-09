package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ChronometerTest extends Activity {
	Chronometer mChrono;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choronometertest);
		
		mChrono = (Chronometer)findViewById(R.id.chrono);
	}
	
	public void onDestroy() {
		super.onDestroy();
		mChrono.stop();
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnstart:
			mChrono.start();
			break;
		case R.id.btnstop:
			mChrono.stop();
			break;
		case R.id.btnreset:
			mChrono.setBase(SystemClock.elapsedRealtime());
			break;
		}
	}
}
