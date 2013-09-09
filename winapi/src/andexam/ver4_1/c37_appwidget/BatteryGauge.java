package andexam.ver4_1.c37_appwidget;

import andexam.ver4_1.*;
import android.appwidget.*;
import android.content.*;
import android.widget.*;

public class BatteryGauge extends AppWidgetProvider {
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, 
			int[] appWidgetIds) {
		/* BR에서 BR을 호출할 수 없다.
		Intent bat = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		String s = bat.getIntExtra("level", -1) + "%";

		RemoteViews views = new RemoteViews(context.getPackageName(), 
				R.layout.batterygauge);
		views.setTextViewText(R.id.gauge, s);
		appWidgetManager.updateAppWidget(appWidgetIds, views);
		*/
		Intent intent = new Intent(context, BatteryGaugeService.class);
		context.startService(intent);
	}

	public void onDeleted(Context context) {
		Intent intent = new Intent(context, BatteryGaugeService.class);
		context.stopService(intent);
	}
}
