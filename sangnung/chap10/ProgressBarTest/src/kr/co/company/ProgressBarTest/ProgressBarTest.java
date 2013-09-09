package kr.co.company.ProgressBarTest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class ProgressBarTest extends Activity {
	private static final int PROGRESS = 0x1;

	private ProgressBar mProgress;
	private int mProgressStatus = 0;

	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.main);

		mProgress = (ProgressBar) findViewById(R.id.ProgressBar01);
		// 백그라운드 스레드
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (mProgressStatus < 100)
						mProgressStatus += 10;
					else
						mProgressStatus = 0;

					// 프로그레스 바를 업데이트한다.
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							mProgress.setProgress(mProgressStatus);
						}
					});
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}
}