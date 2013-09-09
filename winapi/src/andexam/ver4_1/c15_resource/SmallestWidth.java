package andexam.ver4_1.c15_resource;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.util.*;
import android.widget.*;

public class SmallestWidth extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smallestwidth);

		DisplayMetrics dm = getResources().getDisplayMetrics();
		String str = "" + dm.widthPixels + "*" +
			dm.heightPixels + "(" + dm.densityDpi + "dpi)"; 
		TextView info = (TextView)findViewById(R.id.result);
		info.setText(str);
	}
}