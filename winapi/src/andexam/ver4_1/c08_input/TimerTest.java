package andexam.ver4_1.c08_input;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;

/* 핸들러 멤버
public class TimerTest extends Activity {
	int value=0;
	TextView mText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timertest);

		mText=(TextView)findViewById(R.id.text);
		mHandler.sendEmptyMessage(0);
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			value++;
			mText.setText("Value = " + value);
			mHandler.sendEmptyMessageDelayed(0,1000);
		}
	};
}
//*/

/* 핸들러 멤버 - 이름있는 클래스 정의
public class TimerTest extends Activity {
	int value=0;
	TextView mText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timertest);

		mText=(TextView)findViewById(R.id.text);
		mHandler.sendEmptyMessage(0);
	}

	class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			value++;
			mText.setText("Value = " + value);
			mHandler.sendEmptyMessageDelayed(0,1000);
		}
	}
	Handler mHandler = new MyHandler();
}
//*/

/* onCreate에서 생성한 핸들러
public class TimerTest extends Activity {
	int value=0;
	TextView mText;
	Handler mHandler;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timertest);

		mText=(TextView)findViewById(R.id.text);

		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				value++;
				mText.setText("Value = " + value);
				if (value < 5) {
					mHandler.sendEmptyMessageDelayed(0,1000);
				}
			}
		};
		mHandler.sendEmptyMessage(0);
	}
}
//*/

//* 타이머 객체
public class TimerTest extends Activity {
	int value=0;
	TextView mText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timertest);

		mText=(TextView)findViewById(R.id.text);

		new CountDownTimer(10 * 1000, 1000) {
			public void onTick(long millisUntilFinished) {
				value++;
				mText.setText("Value = " + value);
				if (value == 5) {
					cancel();
				}
			}
			public void onFinish() {
			}
		}.start();
	}
}
//*/

