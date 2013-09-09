package kr.co.infinity.BatterySDCardTest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BatterySDCardTest extends Activity {

	TextView textfield;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textfield = (TextView) findViewById(R.id.textfield);
	}

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		filter.addAction(Intent.ACTION_BATTERY_LOW);
		filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
		filter.addAction(Intent.ACTION_POWER_CONNECTED);
		filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		filter.addAction(Intent.ACTION_MEDIA_REMOVED);
		registerReceiver(receiver, filter);
	}

	@Override
	public void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Toast.makeText(context, action, Toast.LENGTH_LONG).show();
			textfield.setText(action);
			if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
				int maxvalue = intent
						.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
				int value = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
				int level = value * 100 / maxvalue;
				textfield.setText(action + "\n현재 배터리 레벨=" + level);
			} else if (action.equals(Intent.ACTION_BATTERY_LOW)) {
				textfield.setText(action + "\n배터리 부족");
			} else if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
				textfield.setText(action + "\nSD카드 장착");
			} else if (action.equals(Intent.ACTION_BATTERY_LOW)) {
				textfield.setText(action + "\nSD카드 장착 해제");
			}
		}
	};
}
