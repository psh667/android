package andexam.ver4_1.external;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class NewsController extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newscontroller);
	}

	public void mOnClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.newsstart:
			intent = new Intent("andexam.ver4_1.NEWS");
			startService(intent);
			break;
		case R.id.newsend:
			intent = new Intent("andexam.ver4_1.NEWS");
			stopService(intent);
			break;
		}
	}
}