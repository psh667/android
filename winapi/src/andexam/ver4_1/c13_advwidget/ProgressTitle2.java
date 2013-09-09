package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ProgressTitle2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.progresstitle2);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.start:
			setProgressBarIndeterminateVisibility(true);
			break;
		case R.id.stop:
			setProgressBarIndeterminateVisibility(false);
			break;
		}
	}
}