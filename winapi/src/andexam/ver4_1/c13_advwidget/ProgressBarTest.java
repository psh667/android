package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ProgressBarTest extends Activity {
	ProgressBar mProgBar;
	ProgressBar mProgCircle;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbartest);
		
		mProgBar = (ProgressBar)findViewById(R.id.progress);
		mProgCircle = (ProgressBar)findViewById(R.id.progcircle);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.decfirst:
			mProgBar.incrementProgressBy(-2);
			break;
		case R.id.incfirst:
			mProgBar.incrementProgressBy(2);
			break;
		case R.id.decsecond:
			mProgBar.incrementSecondaryProgressBy(-2);
			break;
		case R.id.incsecond:
			mProgBar.incrementSecondaryProgressBy(2);
			break;
		case R.id.start:
			mProgCircle.setVisibility(View.VISIBLE);
			break;
		case R.id.stop:
			mProgCircle.setVisibility(View.INVISIBLE);
			break;
		}
	}
}