package andexam.ver4_1.c35_setting;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.os.PowerManager.*;
import android.view.*;
import android.widget.*;

public class WakeAlways extends Activity {
	PowerManager mPm;
	WakeLock mWakeLock;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wakealways);

		mPm = (PowerManager)getSystemService(Context.POWER_SERVICE);
		mWakeLock = mPm.newWakeLock(PowerManager.FULL_WAKE_LOCK, 
				"WakeAlways");
	}

	protected void onResume() {
		super.onResume();
		mWakeLock.acquire();
	}

	protected void onPause() {
		super.onPause();
		if (mWakeLock.isHeld()) {
			mWakeLock.release();
		}
	}
}
