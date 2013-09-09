package andexam.ver4_1.c37_appwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.os.*;
import android.widget.*;

public class BatteryGaugeService extends Service {
	public int onStartCommand (Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(mBRBattery, filter);
		return START_STICKY;
	}

	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBRBattery);
	}

	BroadcastReceiver mBRBattery = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
				int scale, level, ratio;
				scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
				level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
				ratio = level * 100 / scale;

				RemoteViews remote = new RemoteViews(context.getPackageName(), 
						R.layout.batterygauge);
				remote.setTextViewText(R.id.gauge, "" + ratio + "%");
				AppWidgetManager wm = AppWidgetManager.getInstance(
						BatteryGaugeService.this);
				ComponentName widget = new ComponentName(context, BatteryGauge.class);
				wm.updateAppWidget(widget, remote);
			}
		}
	};
};
