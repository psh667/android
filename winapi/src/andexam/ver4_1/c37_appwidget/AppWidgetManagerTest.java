package andexam.ver4_1.c37_appwidget;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.appwidget.*;
import android.os.*;
import android.widget.*;

public class AppWidgetManagerTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appwidgetmanagertest);
		
		AppWidgetManager mAWM = AppWidgetManager.getInstance(this);
		List<AppWidgetProviderInfo> mList = mAWM.getInstalledProviders();
		
		String result = "count = " + mList.size() + "\n";
		for (AppWidgetProviderInfo info : mList) {
			result += info.toString() + "\n\n";
		}
		TextView resulttxt = (TextView)findViewById(R.id.result);
		resulttxt.setText(result);
	}
}
