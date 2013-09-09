package andexam.ver4_1.c19_thread;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class LongTime4 extends Activity {
	int mValue;
	TextView mText;
	ProgressDialog mProgress;
	boolean mQuit;
	UpdateThread mThread;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.longtime);

		mText=(TextView)findViewById(R.id.text);
	}
	
	@SuppressWarnings("deprecation")
	public void mOnClick(View v) {
		mValue = 0;
		showDialog(0);
		mQuit = false;
		mThread = new UpdateThread();
		mThread.start();
	}

	@SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			mProgress = new ProgressDialog(this);
			mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgress.setTitle("Updating");
			mProgress.setMessage("Wait...");
			mProgress.setCancelable(false);
			mProgress.setButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					mQuit = true;
					dismissDialog(0);
				}
			});
			return mProgress;
		}
		return null;
	}

	Handler mHandler = new Handler() {
		@SuppressWarnings("deprecation")
		public void handleMessage(Message msg) {
			mValue = msg.arg1;
			mText.setText(Integer.toString(mValue));
			if (mValue < 100) {
				mProgress.setProgress(mValue);
			} else {
				mQuit = true;
				dismissDialog(0);
			}
		}
	};

	class UpdateThread extends Thread {
		public void run() {
			while (mQuit == false) {
				mValue++;
				Message msg = mHandler.obtainMessage();
				msg.arg1 = mValue;
				mHandler.sendMessage(msg);
				try { Thread.sleep(50); } catch (InterruptedException e) {;}
			}
		}
	}
}
