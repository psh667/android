package kr.co.infinity.ProgressBarTest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class ProgressBarTest extends Activity {
	private static final int PROGRESS = 0x1;
	private ProgressBar mProgress;
	private int mProgressStatus = 0;
	private Handler mHandler = new Handler();
	int i = 0;

	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		mProgress = (ProgressBar) findViewById(R.id.progress_bar);

		// Start lengthy operation in a background thread
		new Thread(new Runnable() {
			public void run() {
				while (mProgressStatus < 100) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					mProgressStatus = i++;

					// Update the progress bar
					mHandler.post(new Runnable() {
						public void run() {
							mProgress.setProgress(mProgressStatus);
						}
					});
				}
			}
		}).start();
	}
}