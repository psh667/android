package andexam.ver4_1.c18_process;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Overlay extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window win = getWindow();
		win.setContentView(R.layout.overlay);

		LayoutInflater inflater = (LayoutInflater)getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout linear = (LinearLayout)inflater.inflate(R.layout.overlay2, null);
		LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		win.addContentView(linear, paramlinear);
	}
}
