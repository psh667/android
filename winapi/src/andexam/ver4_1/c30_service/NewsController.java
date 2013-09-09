package andexam.ver4_1.c30_service;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class NewsController extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newscontroller);
	}
	
	public void mOnClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.newsstart:
			intent = new Intent(this, NewsService.class);
			startService(intent);
			break;
		case R.id.newsend:
			intent = new Intent(this, NewsService.class);
			stopService(intent);
			break;
		}
	}	
}