package kr.co.infinity.TelephonyTest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

public class TelephonyTest extends Activity {
	private TelephonyManager manager;
	PhoneStateListener listener = new PhoneStateListener() {
		public void onCallStateChanged(int state, String incomingNumber) {
			Toast.makeText(TelephonyTest.this,
					"상태:  " + state + "전화번호: " + incomingNumber, 0).show();

		}

		public void onDataActivity(int direction) {
			Toast.makeText(TelephonyTest.this, "방향 : " + direction, 0).show();
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String s = "상태: " + manager.getCallState() + "\n전화번호: "
				+ manager.getLine1Number() + "\n데이터 상태: "
				+ manager.getDataState() + "\n장치 아이디: " + manager.getDeviceId()
				+ "\n네트워크 타입: " + manager.getNetworkType() + "\n전화기 타입: "
				+ manager.getPhoneType() + "\n로밍 여부: "
				+ manager.isNetworkRoaming();
		TextView text = (TextView) findViewById(R.id.text);
		text.setText(s);
		manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE
				| PhoneStateListener.LISTEN_DATA_ACTIVITY);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		manager.listen(listener, PhoneStateListener.LISTEN_NONE);
	}

}