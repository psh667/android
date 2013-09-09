package andexam.ver4_1.c37_appwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class FakeNewsConfig extends Activity {
	final static String PREF = "FakeNews";
	CheckBox mRed;
	int mId;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fakenewsconfig);

		// 일단 실패로 가정한다.
		setResult(RESULT_CANCELED);
		mRed = (CheckBox)findViewById(R.id.chkred);

		// ID 조사해 둔다.
		Intent intent = getIntent();
		mId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 
				AppWidgetManager.INVALID_APPWIDGET_ID);
		Log.d(FakeNews.TAG, "Config onCreate id = " + mId);

		// 설정값 읽어서 체크 박스에 출력.
		SharedPreferences prefs = getSharedPreferences(PREF, 0);
		boolean isRed = prefs.getBoolean("red_" + mId, false);
		mRed.setChecked(isRed);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnok:
			Log.d(FakeNews.TAG, "Install id = " + mId);
			// 인스턴스의 정보에 체크 박스값 저장
			SharedPreferences prefs = getSharedPreferences(PREF, 0);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean("red_" + mId, mRed.isChecked());
			editor.commit();

			// 상태 갱신
			Context con = FakeNewsConfig.this;
			FakeNews.UpdateNews(con, AppWidgetManager.getInstance(con), mId);

			// OK 리턴 보냄
			Intent intent = new Intent();
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mId);
			setResult(RESULT_OK, intent);
			finish();

			break;
		}
	}
}
