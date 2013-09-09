package kr.co.company.AppWidgetTest1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyAppWidget extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			RemoteViews updateViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_word);

			updateViews.setTextViewText(R.id.title, "¿À´ÃÀÇ ¸í¾ð");
			updateViews.setTextViewText(R.id.type, "....");
			updateViews.setTextViewText(R.id.saying,
					"A bird in the hand is worth two in the bush");
			appWidgetManager.updateAppWidget(appWidgetId, updateViews);
		}
	}

	public static String APPWDIEGT_REQUEST = "kr.co.company.APPWIDGET_REQUEST";

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if (APPWDIEGT_REQUEST.equals(intent.getAction())) {
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			ComponentName widget = new ComponentName(context, MyAppWidget.class);
			int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widget);

			final int N = appWidgetIds.length;
			for (int i = 0; i < N; i++) {
				int appWidgetId = appWidgetIds[i];
				RemoteViews updateViews = new RemoteViews(
						context.getPackageName(), R.layout.widget_word);

				updateViews.setTextViewText(R.id.title, "¿À´ÃÀÇ ¸í¾ð");
				updateViews.setTextViewText(R.id.type, "....");
				updateViews.setTextViewText(R.id.saying,
						"Bad news travels fast");
				appWidgetManager.updateAppWidget(appWidgetId, updateViews);
			}

		}
	}
}