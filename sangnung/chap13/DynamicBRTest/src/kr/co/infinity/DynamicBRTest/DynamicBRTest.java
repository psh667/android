package kr.co.infinity.DynamicBRTest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

public class DynamicBRTest extends Activity {

	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "SMS가 수신되었습니다.", Toast.LENGTH_LONG).show();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(receiver, filter);
	}

	public void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

}