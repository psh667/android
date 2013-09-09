package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ProgressTitle extends Activity {
	int mProg;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.progresstitle);

		mProg=5000;
		setProgress(mProg);
		setProgressBarVisibility(true);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.decfirst:
			if (mProg >= 200) mProg -= 200;
			setProgress(mProg);
			break;
		case R.id.incfirst:
			if (mProg <= 9800) mProg += 200;
			setProgress(mProg);
		}
	}	
}