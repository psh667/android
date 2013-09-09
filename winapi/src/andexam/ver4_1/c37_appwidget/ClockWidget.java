package andexam.ver4_1.c37_appwidget;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.widget.*;

/* 타이머를 쓰는 방법
public class ClockWidget extends AppWidgetProvider {
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, 
			int[] appWidgetIds) {
		Timer timer = new Timer();
		ClockTask task = new ClockTask(context, appWidgetManager, appWidgetIds);
		timer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	class ClockTask extends TimerTask {
		Context mContext;
		AppWidgetManager mManager;
		int[] mIds;
		public ClockTask(Context context, AppWidgetManager appWidgetManager, 
			int[] appWidgetIds) {
			mContext = context;
			mManager = appWidgetManager;
			mIds = appWidgetIds;
		}
		
		public void run() {
			RemoteViews remote = new RemoteViews(mContext.getPackageName(), 
					R.layout.clockwidget);
			GregorianCalendar cal = new GregorianCalendar();
			String now = String.format("%d:%d:%d", 
					cal.get(Calendar.HOUR), 
					cal.get(Calendar.MINUTE),
					cal.get(Calendar.SECOND));
			remote.setTextViewText(R.id.nowtime, now);
			mManager.updateAppWidget(mIds, remote);
			
			//AudioManager am = (AudioManager)mContext.getSystemService(
			//	Context.AUDIO_SERVICE);
			//am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
		}
	}
}
//*/

//* 알람을 쓰는 방법
public class ClockWidget extends AppWidgetProvider {
	AlarmManager mAlarm;
	PendingIntent mPending;

	public void onEnabled(Context context) {
		super.onEnabled(context);

		AlarmManager am=(AlarmManager)context.getSystemService(
				Context.ALARM_SERVICE);
		Intent intent = new Intent(context, ClockWidget.class);
		intent.setAction("ClockUpdate");
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 
				1000 , pending);
	}
	
	public void onDisabled(Context context) {
		AlarmManager am=(AlarmManager)context.getSystemService(
				Context.ALARM_SERVICE);
		Intent intent = new Intent(context, ClockWidget.class);
		intent.setAction("ClockUpdate");
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.cancel(pending);
		
		super.onDisabled(context);

	}
	  
	public void onReceive(Context context, Intent intent)  {    
		super.onReceive(context, intent);
		if (intent.getAction().equals("ClockUpdate")) {
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			ComponentName thiswidget = new ComponentName(context, ClockWidget.class);
			int[] ids = appWidgetManager.getAppWidgetIds(thiswidget);
			onUpdate(context, appWidgetManager, ids);
		}
	}
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, 
			int[] appWidgetIds) {
		RemoteViews remote = new RemoteViews(context.getPackageName(), 
				R.layout.clockwidget);
		GregorianCalendar cal = new GregorianCalendar();
		String now = String.format("%d:%d:%d", 
				cal.get(Calendar.HOUR), 
				cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND));
		remote.setTextViewText(R.id.nowtime, now);
		appWidgetManager.updateAppWidget(appWidgetIds, remote);
	}
}
//*/

