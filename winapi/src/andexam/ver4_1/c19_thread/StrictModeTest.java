package andexam.ver4_1.c19_thread;

import java.io.*;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;

public class StrictModeTest extends Activity {
	final static boolean DEBUG_MODE = true;
	public void onCreate(Bundle savedInstanceState) {
		if (DEBUG_MODE) {
			StrictMode.ThreadPolicy thPolicy = new StrictMode.ThreadPolicy.Builder()
			.detectAll()
			.penaltyDialog()
			//.penaltyLog()
			.build();
			StrictMode.setThreadPolicy(thPolicy);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.strictmodetest);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnfile:
			try {
				FileOutputStream fos = openFileOutput("test.txt", 
						Context.MODE_WORLD_READABLE);
				String str = "Android File IO Test";
				fos.write(str.getBytes());
				fos.close();
			} catch (Exception e) {;}
			break;
		}
	}
}