package andexam.ver4_1.c19_thread;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

/* 핸들러로 지연 호출 
public class Post extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);
	}

	public void mOnClick(View v) {
		new AlertDialog.Builder(this)
		.setTitle("질문")
		.setMessage("업로드 하시겠습니까?")
		.setPositiveButton("예", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				mHandler.sendEmptyMessageDelayed(0,10);
			}
		})
		.setNegativeButton("아니오", null)
		.show();
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				doUpload();
			}
		}
	};

	void doUpload() {
		for (int i = 0; i < 20; i++) {
			try { Thread.sleep(100); } catch (InterruptedException e) {;}
		}
		Toast.makeText(this, "업로드를 완료했습니다.", 0).show();
	}
}
//*/

/* 러너블 전달 
public class Post extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);
	}

	public void mOnClick(View v) {
		new AlertDialog.Builder(this)
		.setTitle("질문")
		.setMessage("업로드 하시겠습니까?")
		.setPositiveButton("예", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				mHandler.postDelayed(new Runnable() {
					public void run() {
						doUpload();
					}
				},10);
			}
		})
		.setNegativeButton("아니오", null)
		.show();
	}

	Handler mHandler = new Handler();

	void doUpload() {
		for (int i = 0; i < 20; i++) {
			try { Thread.sleep(100); } catch (InterruptedException e) {;}
		}
		Toast.makeText(this, "업로드를 완료했습니다.", 0).show();
	}
}
//*/

//* 뷰의 postDelayed 호출
public class Post extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);
	}

	public void mOnClick(View v) {
		new AlertDialog.Builder(this)
		.setTitle("질문")
		.setMessage("업로드 하시겠습니까?")
		.setPositiveButton("예", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Button btnUpload = (Button)findViewById(R.id.upload);
				btnUpload.postDelayed(new Runnable() {
					public void run() {
						doUpload();
					}
				},10);
			}
		})
		.setNegativeButton("아니오", null)
		.show();
	}

	void doUpload() {
		for (int i = 0; i < 20; i++) {
			try { Thread.sleep(100); } catch (InterruptedException e) {;}
		}
		Toast.makeText(this, "업로드를 완료했습니다.", 0).show();
	}
}
//*/