package andexam.ver4_1.c36_tel;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.telephony.*;
import android.view.*;
import android.widget.*;

public class YieldCall extends Activity {
	TelephonyManager mTelMan;
	MediaPlayer mPlayer;
	TextView mTextCall;
	boolean mResumeAfterCall;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yieldcall);

		mTextCall = (TextView)findViewById(R.id.txtcall);

		mPlayer = new MediaPlayer();
		try {
			String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
			mPlayer.setDataSource(sd + "/eagle5.mp3");
			mPlayer.prepare();
		} catch (Exception e) {
			Toast.makeText(this, "error : " + e.getMessage(), 0).show();
		}

		mTelMan = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		mTelMan.listen(mCallListener, PhoneStateListener.LISTEN_CALL_STATE);

		// 파일 재생 및 일시 중지
		findViewById(R.id.btnplay).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (mPlayer.isPlaying()) {
					mPlayer.pause();
				} else {
					mPlayer.start();
				}
			}
		});
	}

	PhoneStateListener mCallListener = new PhoneStateListener() {
		public void onCallStateChanged (int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				mTextCall.setText("전화 대기중");
				if (mResumeAfterCall) {
					mTextCall.postDelayed(new Runnable() {
						public void run() {
							mPlayer.start();
						}
					}, 3000);
				}
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				mTextCall.setText("전화 왔어요 : " + incomingNumber);
				// 전화오면 즉시 재생 중지
				if (mPlayer.isPlaying()) {
					mPlayer.pause();
					mResumeAfterCall = true;
				} else {
					mResumeAfterCall = false;
				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				mTextCall.setText("통화중....");
				break;
			}
		}
	};

	public void onDestroy() {
		super.onDestroy();
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
		mTelMan.listen(mCallListener, PhoneStateListener.LISTEN_NONE);
	}
}

