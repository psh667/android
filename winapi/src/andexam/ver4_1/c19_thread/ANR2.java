package andexam.ver4_1.c19_thread;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ANR2 extends Activity {
	boolean bUploading = false;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anr);
	}
	
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnincrease:
			TextView textCounter=(TextView)findViewById(R.id.txtcounter);
			int count = Integer.parseInt(textCounter.getText().toString());
			textCounter.setText(Integer.toString(count + 1));
			break;
		case R.id.btnupload:
			if (bUploading) return;
			Thread uploadThread = new Thread() {
				public void run() {
					doUpload();
					mCompleteHandler.sendEmptyMessage(0);
				}
			};
			bUploading = true;
			uploadThread.start();
			break;
		}
	}

	public Handler mCompleteHandler = new Handler() {
		public void handleMessage(Message msg) {
			bUploading = false;
			Toast.makeText(ANR2.this, "업로드를 완료했습니다.", 0).show();
		}
	};
	
	void doUpload() {
		for (int i = 0; i < 100; i++) {
			try { Thread.sleep(100); } catch (InterruptedException e) {;}
		}
	}
}