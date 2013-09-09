package andexam.ver4_1.c25_file;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class TextLogTest extends Activity {
	LinearLayout mLinear;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textlogtest);

		// onCreate에서 로그 유틸리티 초기화
		TextLog.init(this);
		TextLog.mAppendTime = true;
		TextLog.mReverseReport = true;

		mLinear = (LinearLayout)findViewById(R.id.linear);
		mLinear.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// 필요할 때 로그 기록
					lg.o("down. x = " + (int)event.getX() + 
							", y = " + (int)event.getY());
					return true;
				case MotionEvent.ACTION_MOVE:
					lg.o("move. x = " + (int)event.getX() + 
							", y = " + (int)event.getY());
					return true;
				}
				return false;
			}
		});
	}

	// 다음 두 메서드를 디버깅 프로젝트의 엑티비티에 추가한다.
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		TextLog.addMenu(menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (TextLog.execMenu(item) == true) {
			return true;
		}
		return false;
	}
}