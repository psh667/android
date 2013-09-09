package andexam.ver4_1.c18_process;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;

public class Center extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Window win = getWindow();
		win.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, 
				ViewGroup.LayoutParams.WRAP_CONTENT);
		win.setGravity(Gravity.CENTER);

		setContentView(R.layout.center);
	}
}
