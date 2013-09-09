package andexam.ver4_1.c37_appwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.widget.*;

public class FakeNewsDetail extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fakenewsdetail);

		Intent intent = getIntent();
		int newsid = intent.getIntExtra("newsid", 100); 
		Log.d(FakeNews.TAG, "Config news id = " + newsid);
		TextView detail = (TextView)findViewById(R.id.detailnews);
		detail.setText("상세 뉴스 보기 : " + newsid);
	}
}
