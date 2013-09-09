package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class SlidingDrawerTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidingdrawertest);
	}

	public void mOnClick(View v) {
		SlidingDrawer drawer = (SlidingDrawer)findViewById(R.id.drawer);
		drawer.animateClose();
	}	
}
