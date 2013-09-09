package andexam.ver4_1.c37_appwidget;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.graphics.*;
import android.util.*;
import android.widget.*;

public class FakeNews extends AppWidgetProvider {
	final static String ACTION_FAKENEWS_CHANGE = "FakeNewsChange";
	final static String PREF = "FakeNews";
	final static String TAG = "FakeNews";

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, 
			int[] appWidgetIds) {
		Log.d(TAG, "onUpdate, length = " + appWidgetIds.length + 
				", id = " + appWidgetIds[0]);
		for (int i = 0; i < appWidgetIds.length; i++) {
			UpdateNews(context, appWidgetManager, appWidgetIds[i]); 
		}
	}

	static void UpdateNews(Context context, AppWidgetManager appWidgetManager, 
			int widgetId) {
		String[] arNews = {
				"1.안드로이드 SDK 7.0 모카빵 발표",
				"2.기술 혁신으로 10만원대 8테라 SSD 등장",
				"3.손목 시계형 안드로이드 스마트폰 발표",
				"4.눈알로 움직이는 무접촉 터치 스크린 개발",
				"5.한번 충전으로 1년 쓰는 괴물 배터리 상용화",
		};

		int newsid = new Random().nextInt(arNews.length);
		RemoteViews views = new RemoteViews(context.getPackageName(), 
				R.layout.fakenews);
		views.setTextViewText(R.id.news, arNews[newsid]);
		SharedPreferences prefs = context.getSharedPreferences(PREF, 0);
		boolean isRed = prefs.getBoolean("red_" + widgetId, false);
		views.setTextColor(R.id.news, isRed ? Color.RED:Color.BLACK);
		Log.d(TAG, "UpdateNews, id = " + widgetId);

		Intent intent = new Intent(context, FakeNewsDetail.class);
		intent.putExtra("newsid", newsid);
		PendingIntent pending = PendingIntent.getActivity(context, widgetId, 
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.mainlayout, pending);

		Intent intent2 = new Intent(context, FakeNewsConfig.class);
		intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		PendingIntent pending2 = PendingIntent.getActivity(context, widgetId, 
				intent2, 0);
		views.setOnClickPendingIntent(R.id.btnconfig, pending2);

		Intent intent3 = new Intent(context, FakeNews.class);
		intent3.setAction(ACTION_FAKENEWS_CHANGE);
		intent3.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		PendingIntent pending3 = PendingIntent.getBroadcast(context, widgetId, 
				intent3, 0);
		views.setOnClickPendingIntent(R.id.btnchange, pending3);

		appWidgetManager.updateAppWidget(widgetId, views);
	}

	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action != null && action.equals(ACTION_FAKENEWS_CHANGE)) {
			int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 
					AppWidgetManager.INVALID_APPWIDGET_ID);
			UpdateNews(context, AppWidgetManager.getInstance(context), id);
			Log.d(TAG, "onReceive(CHANGE), id = " + id);
			return;
		}
		super.onReceive(context, intent);
	}

	public void onDeleted(Context context, int[] appWidgetIds) {
		for (int i = 0; i < appWidgetIds.length; i++) {
			Log.d(TAG, "onDeleted, id = " + appWidgetIds[i]);
			SharedPreferences prefs = context.getSharedPreferences(PREF, 0);
			SharedPreferences.Editor editor = prefs.edit();
			editor.remove("red_" + appWidgetIds[i]);
			editor.commit();
		}
	}

	public void onEnabled(Context context) {
		Log.d(TAG, "onEnabled");
	}

	public void onDisabled(Context context) {
		Log.d(TAG, "onDisabled");
	}
}
