package andexam.ver4_1.c15_resource;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.util.*;
import android.widget.*;

public class DisplayMetricsTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaymetricstest);

		//DisplayMetrics dm = new DisplayMetrics();
		//getWindowManager().getDefaultDisplay().getMetrics(dm);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		String str = "widthPixels=" + dm.widthPixels +
			"\nheightPixels=" + dm.heightPixels +
			"\ndensityDpi=" + dm.densityDpi + 
			"\ndensity=" + dm.density + 
			"\nscaledDensity=" + dm.scaledDensity + 
			"\nxdpi=" + dm.xdpi + 
			"\nydpi=" + dm.ydpi;
		TextView info = (TextView)findViewById(R.id.result);
		info.setText(str);
	}
}