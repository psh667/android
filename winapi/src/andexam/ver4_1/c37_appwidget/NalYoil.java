package andexam.ver4_1.c37_appwidget;

import andexam.ver4_1.*;
import java.util.*;

import android.appwidget.*;
import android.content.*;
import android.widget.*;

public class NalYoil extends AppWidgetProvider {
	String[] arYoil={"일", "월", "화", "수", "목", "금", "토" };
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, 
			int[] appWidgetIds) {
		RemoteViews remote = new RemoteViews(context.getPackageName(), 
			R.layout.nalyoil);
		GregorianCalendar cal = new GregorianCalendar();
		String nal = String.format("%d월 %d일", 
				cal.get(Calendar.MONTH) + 1, 
				cal.get(Calendar.DAY_OF_MONTH));
		remote.setTextViewText(R.id.nal, nal);
		remote.setTextViewText(R.id.yoil, arYoil[cal.get(Calendar.DAY_OF_WEEK) - 1]);
		appWidgetManager.updateAppWidget(appWidgetIds, remote);
	}
}
