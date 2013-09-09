package andexam.ver4_1.c37_appwidget;

import andexam.ver4_1.*;
import java.util.*;

import android.appwidget.*;
import android.content.*;
import android.widget.*;

public class AnnivListWidget extends AppWidgetProvider {
	public AnnivListWidget() {
	}
	
	public void onDeleted(Context context) {
	}
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int i = 0;i<appWidgetIds.length;i++) {
			RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.annivlistwidget);
			Intent intent = new Intent(context, AnnivListWidgetService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
			rv.setRemoteAdapter(R.id.annivlist, intent);
			rv.setEmptyView(R.id.annivlist, R.id.emptyview);
			appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	public static class AnnivListWidgetService extends RemoteViewsService {
		public RemoteViewsFactory onGetViewFactory(Intent intent) {
			return new AnnivListFactory(this.getApplicationContext(), intent);
		}
	}
}

class AnnivListFactory implements RemoteViewsService.RemoteViewsFactory {
	Context mContext;
	int mAppWidgetId;
	ArrayList<String> arAnniv = new ArrayList<String>(); 
	
	public AnnivListFactory(Context context, Intent intent) {
		mContext = context;
		mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	public void onCreate() {
		arAnniv.add("1월 1일(음) - 설날"); 
		arAnniv.add("3월 1일(양) - 삼일절");
		arAnniv.add("4월 1일(양) - 만우절");
		arAnniv.add("4월 5일(양) - 식목일");
		arAnniv.add("4월 8일(음) - 석가탄신일");
		arAnniv.add("4월 18일(음) - 마님 생일");
		arAnniv.add("4월 28일(양) - 충무공탄신");
		arAnniv.add("5월 5일(양) - 어린이날");
		arAnniv.add("6월 6일(양) - 현충일");
		arAnniv.add("6월 29일(음) - 내생일");
		arAnniv.add("7월 17일(양) - 제헌절");
		arAnniv.add("8월 15일(양) - 광복절");
		arAnniv.add("8월 15일(음) - 추석");
		arAnniv.add("10월 1일(양) - 국군의 날");
		arAnniv.add("10월 3일(양) - 개천절");
		arAnniv.add("10월 9일(양) - 한글날");
		arAnniv.add("11월 7일(양) - 결혼기념일");
		arAnniv.add("11월 11일(양) - 빼빼로데이");
		arAnniv.add("12월 25일(양) - 성탄절");
	}

	public void onDestroy() {
	}

	public int getCount() {
		return arAnniv.size();
	}

	public long getItemId(int position) {
		return position;
	}

	public int getViewTypeCount() {
		return 1;
	}

	public RemoteViews getLoadingView() {
		return null;
	}

	public RemoteViews getViewAt(int position) {
		RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.annivlistwidgetitem);
		rv.setTextViewText(R.id.annivtext, arAnniv.get(position));
		return rv;
	}

	public boolean hasStableIds() {
		return true;
	}

	public void onDataSetChanged() {
	}
}
