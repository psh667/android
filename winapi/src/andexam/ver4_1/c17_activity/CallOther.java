package andexam.ver4_1.c17_activity;

import andexam.ver4_1.*;
import java.io.*;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class CallOther extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.callother);
	}

	public void mOnClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.web:
			intent = new Intent(Intent.ACTION_VIEW, 
					Uri.parse("http://www.google.com"));
			startActivity(intent); 
			break;
		case R.id.dial:
			intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:015-123-4567"));
			startActivity(intent);
			break;
		case R.id.picture:
			intent = new Intent(Intent.ACTION_VIEW);
			String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
			Uri uri = Uri.fromFile(new File(sd + "/test.jpg"));
			intent.setDataAndType(uri, "image/jpeg");
			startActivity(intent);
			break;
		case R.id.other:
			intent = new Intent(Intent.ACTION_MAIN);
			intent.setComponent(new ComponentName("exam.memo", "exam.memo.Memo"));
			//intent.setClassName("exam.memo", "exam.memo.Memo");
			startActivity(intent);
			break;
		}
	}
}
