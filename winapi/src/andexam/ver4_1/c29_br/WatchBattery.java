package andexam.ver4_1.c29_br;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;

public class WatchBattery extends Activity {
	TextView mStatus;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.watchbattery);

		mStatus = (TextView)findViewById(R.id.status);
	}
	
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		filter.addAction(Intent.ACTION_BATTERY_LOW);
		filter.addAction(Intent.ACTION_BATTERY_OKAY);
		filter.addAction(Intent.ACTION_POWER_CONNECTED);
		filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
		registerReceiver(mBRBattery, filter);
	}	

	public void onPause() {
		super.onPause();        
		unregisterReceiver(mBRBattery);
	}

	BroadcastReceiver mBRBattery = new BroadcastReceiver() {
		int Count = 0;
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Count++;
			if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
				onBatteryChanged(intent);
			}
			if (action.equals(Intent.ACTION_BATTERY_LOW)) {
				Toast.makeText(context, "배터리 위험 수준", Toast.LENGTH_LONG).show();
			}
			if (action.equals(Intent.ACTION_BATTERY_OKAY)) {
				Toast.makeText(context, "배터리 양호", Toast.LENGTH_LONG).show();
			}
			if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
				Toast.makeText(context, "전원 연결됨", Toast.LENGTH_LONG).show();
			}
			if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
				Toast.makeText(context, "전원 분리됨", Toast.LENGTH_LONG).show();
			}
		}

		public void onBatteryChanged(Intent intent) {
			int plug, status, scale, level, ratio;
			String sPlug = "";
			String sStatus = "";

			if (intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false) == false){
				mStatus.setText("배터리 없음");
				return;
			}

			plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
			status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 
					BatteryManager.BATTERY_STATUS_UNKNOWN);
			scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
			level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			ratio = level * 100 / scale;

			switch (plug) {
			case BatteryManager.BATTERY_PLUGGED_AC:
				sPlug = "AC";
				break;
			case BatteryManager.BATTERY_PLUGGED_USB:
				sPlug = "USB";
				break;
			default:
				sPlug = "Battery";
				break;
			}

			switch (status) {
			case BatteryManager.BATTERY_STATUS_CHARGING:
				sStatus = "충전중";
				break;
			case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
				sStatus = "충전중 아님";
				break;
			case BatteryManager.BATTERY_STATUS_DISCHARGING:
				sStatus = "방전중";
				break;
			case BatteryManager.BATTERY_STATUS_FULL:
				sStatus = "만충전";
				break;
			default:
			case BatteryManager.BATTERY_STATUS_UNKNOWN:
				sStatus = "알 수가 없어";
				break;
			}

			String str = String.format("수신 회수:%d\n연결: %s\n상태:%s\n레벨:%d%%", 
					Count, sPlug, sStatus, ratio);
			mStatus.setText(str);
		}
	};
}