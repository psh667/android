package andexam.ver4_1.c18_process;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;

public class FullScreen extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Window win = getWindow();
		win.requestFeature(Window.FEATURE_NO_TITLE);
		win.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.fullscreen);
	}
}
