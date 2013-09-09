package andexam.ver4_1.c19_thread;

import andexam.ver4_1.*;
import andexam.ver4_1.c19_thread.LongTime4.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class LongTime5 extends Activity {
	int mValue;
	TextView mText;
	ProgressDialog mProgress;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.longtime);

		mText = (TextView)findViewById(R.id.text);
	}
	
	public void mOnClick(View v) {
		new AccumulateTask().execute(100);
	}

	class AccumulateTask extends AsyncTask<Integer, Integer, Integer> {
		@SuppressWarnings("deprecation")
		protected void onPreExecute() {
			mValue = 0;
			mProgress = new ProgressDialog(LongTime5.this);
			mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgress.setTitle("Updating");
			mProgress.setMessage("Wait...");
			mProgress.setCancelable(false);
			mProgress.setProgress(0);
			mProgress.setButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					cancel(true);
				}
			});
			mProgress.show();
		}
		
		protected Integer doInBackground(Integer... arg0) {
			while (isCancelled() == false) { 
				mValue++;
				if (mValue <= 100) {
					publishProgress(mValue);
				} else {
					break;
				}
				try { Thread.sleep(50); } catch (InterruptedException e) {;}
			}
			return mValue;
		}
		
		protected void onProgressUpdate(Integer... progress) {		 
			mProgress.setProgress(progress[0]);	 
			mText.setText(Integer.toString(progress[0]));	 
		}
		
		protected void onPostExecute(Integer result) { 
			mProgress.dismiss();
		}
		
		protected void onCancelled() {
			mProgress.dismiss();
		}
	}
}

