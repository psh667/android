package andexam.ver4_1.c19_thread;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class BackWork2 extends Activity {
	TextView mResult;
	AccumThread mThread;
	ProgressDialog mProgress;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.backwork);

		mResult = (TextView)findViewById(R.id.result);
	}

	@SuppressWarnings("deprecation")
	public void mOnClick(View v) {
		int start = 0, end = 100;

		mProgress = new ProgressDialog(v.getContext());
		mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgress.setTitle("Calculating");
		mProgress.setMessage("Wait...");
		mProgress.setCancelable(false);
		mProgress.setButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				mThread.mQuit = true;
				mProgress.dismiss();
			}
		});
		mProgress.show();

		mThread = new AccumThread(start, end, mAfterAccum);
		mThread.start();
	}

	class AccumThread extends Thread {
		int mStart, mEnd;
		int mResult;
		Handler mAfter;
		boolean mQuit;

		AccumThread(int start, int end, Handler after) {
			mStart = start;
			mEnd = end;
			mQuit = false;
			mAfter = after;
		}

		public void run() {
			mResult = 0;
			for (int i = mStart; i <= mEnd; i++) {
				mResult += i;
				try { Thread.sleep(20); } catch (InterruptedException e) {;}
				if (mQuit) {
					return;
				}
			}
			mAfter.sendEmptyMessage(0);
		}
	}

	Handler mAfterAccum = new Handler() {
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			if (mThread.mQuit == false) {
				mResult.setText("" + mThread.mResult);
			}
		}
	};
}
