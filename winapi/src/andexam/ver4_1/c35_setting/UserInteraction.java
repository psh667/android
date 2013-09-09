package andexam.ver4_1.c35_setting;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;

//* 원론적인 방법
public class UserInteraction extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinteraction);
	}

	protected Handler mFinishHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			finish();
		}
	};

	void registerFinish() {
		mFinishHandler.sendEmptyMessageDelayed(0, 5 * 1000);
	}

	void unRegisterFinish() {
		mFinishHandler.removeMessages(0);
	}

	void RefreshFinish() {
		unRegisterFinish();
		registerFinish();
	}

	protected void onResume() {
		super.onResume();
		registerFinish();
	}

	protected void onPause() {
		unRegisterFinish();
		super.onPause();
	}

	public void onUserInteraction() {
		super.onUserInteraction();
		RefreshFinish();
	}

	protected void onUserLeaveHint () {
		super.onUserLeaveHint();
		Toast.makeText(this, "Leave by user", Toast.LENGTH_LONG).show();
	}
}
//*/

/* 좀 더 쉬운 방법
public class UserInteraction extends Activity {
	long mLastInteraction;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinteraction);
	}

	protected void onResume() {
		super.onResume();
		mLastInteraction = System.currentTimeMillis();
		mFinishHandler.sendEmptyMessageDelayed(0, 1000);
	}
	
	protected void onPause() {
		super.onPause();
		mFinishHandler.removeMessages(0);
	}

	protected Handler mFinishHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mFinishHandler.sendEmptyMessageDelayed(0, 1000);
			long now = System.currentTimeMillis();
			if ((now - mLastInteraction) > 5000) {
				finish();
			}
		}
	};

	public void onUserInteraction() {
		super.onUserInteraction();
		mLastInteraction = System.currentTimeMillis();
	}
}
//*/
