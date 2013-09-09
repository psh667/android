package andexam.ver4_1.c35_setting;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.os.PowerManager.*;
import android.view.*;
import android.widget.*;

public class ReadingCounter extends Activity {
	EditText mStartEdit, mEndEdit, mPeriodEdit;
	CheckBox mSound;
	TextView mNowPageText, mRemainText;
	Button mBtnStart, mBtnPause;
	int mNowPage, mRemain;
	SoundPool mPool;
	int mSheet;
	PowerManager mPm;
	WakeLock mWakeLock;
	final static int IDLE = 0;
	final static int COUNTING = 1;
	final static int PAUSE = 2;
	int mStatus = IDLE;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readingcounter);

		// 위젯 찾음
		mStartEdit = (EditText)findViewById(R.id.edstart);
		mEndEdit = (EditText)findViewById(R.id.edend);
		mPeriodEdit = (EditText)findViewById(R.id.edperiod);
		mSound = (CheckBox)findViewById(R.id.chksound);
		mNowPageText = (TextView)findViewById(R.id.nowpage);
		mRemainText = (TextView)findViewById(R.id.remain);
		
		// 프레퍼런스에서 정보 읽음
		SharedPreferences pref = getSharedPreferences("ReadingCounter",0);
		int start = pref.getInt("start",1);
		int end = pref.getInt("end", 5);
		int period = pref.getInt("period", 10);
		boolean sound = pref.getBoolean("sound", true);
		mStartEdit.setText(Integer.toString(start));
		mEndEdit.setText(Integer.toString(end));
		mPeriodEdit.setText(Integer.toString(period));
		mSound.setChecked(sound);

		// 책장 넘기는 소리
		mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mSheet = mPool.load(this, R.raw.shuffle, 1);

		// 버튼의 핸들러 연결
		mBtnStart = (Button)findViewById(R.id.btnstart); 
		mBtnStart.setOnClickListener(mClickListener);
		mBtnPause = (Button)findViewById(R.id.btnpause); 
		mBtnPause.setOnClickListener(mClickListener);

		// WakeLock 객체 생성
		mPm = (PowerManager)getSystemService(Context.POWER_SERVICE);
		mWakeLock = mPm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "tag");
	}

	// 떠 있는 동안 화면 유지. 백그라운드 카운팅도 실용성 있어 
	//카운팅 중에만 유지하는 정책은 좋지 않음
	protected void onResume() {
		super.onResume();
		mWakeLock.acquire();
	}
	
	protected void onPause() {
		super.onPause();
		if (mWakeLock.isHeld()) {
			mWakeLock.release();
		}

		// 프레퍼런스에 옵션값 기록
		try {
			SharedPreferences pref = getSharedPreferences("ReadingCounter",0);
			SharedPreferences.Editor edit = pref.edit();
			int start, end, period;
			boolean sound;
			start = Integer.parseInt(mStartEdit.getText().toString());
			end = Integer.parseInt(mEndEdit.getText().toString());
			period = Integer.parseInt(mPeriodEdit.getText().toString());
			sound = mSound.isChecked();
			edit.putInt("start", start);
			edit.putInt("end", end);
			edit.putInt("period", period);
			edit.putBoolean("sound", sound);
			edit.commit();
		}
		catch (NumberFormatException e) {
		}
	}
	
	Button.OnClickListener mClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnstart:
				if (mStatus != IDLE) {
					mTimerHandler.removeMessages(0);
				}
				
				// 시작 페이지, 남은 시간만 읽고 타이머 바로 동작.
				// 끝, 시간, 사운드 여부는 중간에 바뀔 수도 있으므로 지금 조사하지 않음
				int endpage;
				try {
					mNowPage = Integer.parseInt(mStartEdit.getText().toString());
					endpage = Integer.parseInt(mEndEdit.getText().toString());
					mRemain = Integer.parseInt(mPeriodEdit.getText().toString()) - 1;
				}
				catch (NumberFormatException e) {
					Toast.makeText(ReadingCounter.this, 
							"독서 범위나 시간이 잘못 입력되었습니다.", 
							Toast.LENGTH_LONG).show();
					return;
				}
				
				if (mNowPage >= endpage) {
					Toast.makeText(ReadingCounter.this, 
							"끝 페이지가 시작 페이지보다 더 뒤쪽이어야 합니다.", 
							Toast.LENGTH_LONG).show();
					return;
				}
				
				mNowPageText.setText(Integer.toString(mNowPage));
				mRemainText.setText(Integer.toString(mRemain));

				mStatus = COUNTING;
				mBtnPause.setText("잠시 중지");
				mTimerHandler.sendEmptyMessageDelayed(0,1000);
				break;
			case R.id.btnpause:
				if (mStatus == IDLE) {
					return;
				}
				if (mStatus == PAUSE) {
					mStatus = COUNTING;
					mBtnPause.setText("잠시 중지");
					mTimerHandler.sendEmptyMessageDelayed(0,1000);
				} else {
					mStatus = PAUSE;
					mBtnPause.setText("계속");
				}
				break;
			}
		}
	};

	Handler mTimerHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (mStatus == PAUSE) {
				return;
			}
			
			if (mRemain != 0) {
				mRemain--;
				mRemainText.setText(Integer.toString(mRemain));
				mTimerHandler.sendEmptyMessageDelayed(0,1000);
			} else {
				// 소리부터 낸다.
				if (mSound.isChecked()) {
					mPool.play(mSheet, 1, 1, 0, 0, 1);
				}

				// 끝 페이지를 지워 버렸으면 즉시 독서를 종료한다.
				int endpage;
				try {
					endpage = Integer.parseInt(mEndEdit.getText().toString());
				}
				catch (NumberFormatException e) {
					endpage = -1;
				}
				
				if (mNowPage < endpage) {
					mNowPage++;
					mNowPageText.setText(Integer.toString(mNowPage));

					// 시간을 지워 버렸으면 60초로 디폴트 처리한다.
					try {
						mRemain = Integer.parseInt(mPeriodEdit.getText().toString()) - 1;
					}
					catch (NumberFormatException e) {
						mRemain = 60;
					}

					mRemainText.setText(Integer.toString(mRemain));
					mTimerHandler.sendEmptyMessageDelayed(0,1000);
				} else {
					Toast.makeText(ReadingCounter.this, 
							"독서가 끝났습니다.", Toast.LENGTH_LONG).show();
					mStatus = IDLE;
				}
			}
		}
	};
}
