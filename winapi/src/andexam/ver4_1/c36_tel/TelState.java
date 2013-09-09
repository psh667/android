package andexam.ver4_1.c36_tel;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.telephony.*;
import android.widget.*;

public class TelState extends Activity {
	TelephonyManager mTelMan;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telstate);

		mTelMan = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		mTelMan.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	public void onDestroy() {
		super.onDestroy();
		mTelMan.listen(mListener, PhoneStateListener.LISTEN_NONE);
	}

	void RefreshState() {
		String callState = "";
		switch (mTelMan.getCallState()) {
		case TelephonyManager.CALL_STATE_IDLE:
			callState = "대기중";
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			callState = "전화 오는중";
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			callState = "통화중";
			break;
		}

		String state = String.format("통화상태:%s\n전화번호:%s\n장치ID:%s\n" +
				"네트워크 유형:%d\n전화 유형:%d\n국가:%s\n사업자:%s\n" +
				"서비스 제공자:%s\nSIM:%s\n가입자:%s\n로밍:%s", 
				callState, mTelMan.getLine1Number(), mTelMan.getDeviceId(),
				mTelMan.getNetworkType(), mTelMan.getPhoneType(), 
				mTelMan.getNetworkCountryIso(), 
				mTelMan.getNetworkOperatorName(), mTelMan.getSimOperatorName(), 
				mTelMan.getSimSerialNumber(), mTelMan.getSubscriberId (),
				mTelMan.isNetworkRoaming() ? "yes":"no");

		TextView result = (TextView)findViewById(R.id.result);
		result.setText(state);
	}

	PhoneStateListener mListener = new PhoneStateListener() {
		public void onCallStateChanged (int state, String incomingNumber) {
			if (state == TelephonyManager.CALL_STATE_RINGING) {
				Toast.makeText(TelState.this, "전화 : " + 
						incomingNumber, 0).show();
			}
			RefreshState();
		}
	};
}