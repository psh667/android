package andexam.ver4_1.c19_thread;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class LongTime3 extends Activity {
	int mValue;
	TextView mText;
	ProgressDialog mProgress;
	boolean mQuit;

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
		mHandler.sendEmptyMessage(0);
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
			mValue++;
			mText.setText(Integer.toString(mValue));
			try { Thread.sleep(50); } catch (InterruptedException e) {;}
			if (mValue < 100 && mQuit == false) {
				mProgress.setProgress(mValue);
				mHandler.sendEmptyMessage(0);
			} else {
				dismissDialog(0);
			}
		}
	};
}