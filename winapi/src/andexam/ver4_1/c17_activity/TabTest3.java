package andexam.ver4_1.c17_activity;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;

@SuppressWarnings("deprecation")
public class TabTest3 extends TabActivity {
	TabHost mTab;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();

		Intent int1 = new Intent(this, andexam.ver4_1.c11_widget.GramPrice.class);
		int1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		tabHost.addTab(tabHost.newTabSpec("tag")
				.setIndicator("Price")
				.setContent(int1));

		Intent int2 = new Intent(this, andexam.ver4_1.c13_advwidget.StopWatch.class);
		int2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		tabHost.addTab(tabHost.newTabSpec("tab2")
				.setIndicator("StopWatch")
				.setContent(int2));
		
		Intent int3 = new Intent(this, andexam.ver4_1.c13_advwidget.SportsScore.class);
		int3.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		tabHost.addTab(tabHost.newTabSpec("tab3")
				.setIndicator("Score")
				.setContent(int3));
	}
}