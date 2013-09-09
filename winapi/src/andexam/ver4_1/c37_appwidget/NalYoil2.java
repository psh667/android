package andexam.ver4_1.c37_appwidget;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.widget.*;

public class NalYoil2 extends AppWidgetProvider {
	String[] arYoil={"일", "월", "화", "수", "목", "금", "토" };
	final static String ACTION_DISPLAY_FULLTIME = "NalYoil.DisplayFullTime";
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, 
			int[] appWidgetIds) {
		for (int i = 0;i<appWidgetIds.length;i++) {
			RemoteViews remote = new RemoteViews(context.getPackageName(), 
				R.layout.nalyoil2);
			GregorianCalendar cal = new GregorianCalendar();
			String nal = String.format("%d월 %d일", 
					cal.get(Calendar.MONTH) + 1, 
					cal.get(Calendar.DAY_OF_MONTH));
			remote.setTextViewText(R.id.nal, nal);
			remote.setTextViewText(R.id.yoil, arYoil[cal.get(Calendar.DAY_OF_WEEK) - 1]);
	
			Intent intent = new Intent(context, NalYoil2.class);
			intent.setAction(ACTION_DISPLAY_FULLTIME);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0); 
			remote.setOnClickPendingIntent(R.id.mainlayout, pendingIntent);
			appWidgetManager.updateAppWidget(appWidgetIds[i], remote);
		}
	}
	
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action != null && action.equals(ACTION_DISPLAY_FULLTIME)) {
			GregorianCalendar cal = new GregorianCalendar();
			String fulltime = String.format("%d년 %d월 %d일 %d:%d:%d",
					cal.get(Calendar.YEAR),cal.get(Calendar.MONTH) + 1, 
					cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR),
					cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));
			Toast.makeText(context, fulltime, 0).show();
			return;
		}
		super.onReceive(context, intent);
	}
}
