package andexam.ver4_1.c19_thread;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class LooperTest extends Activity {
	int mMainValue = 0;
	TextView mMainText;
	TextView mBackText;
	EditText mNumEdit;
	CalcThread mThread;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loopertest);

		mMainText = (TextView)findViewById(R.id.mainvalue);
		mBackText = (TextView)findViewById(R.id.backvalue);
		mNumEdit = (EditText)findViewById(R.id.number);

		mThread = new CalcThread(mHandler);
		mThread.setDaemon(true);
		mThread.start();
	}

	public void mOnClick(View v) {
		Message msg;
		switch (v.getId()) {
		case R.id.increase:
			mMainValue++;
			mMainText.setText("MainValue : " + mMainValue);
			break;
		case R.id.square:
			msg = new Message();
			msg.what = 0;
			msg.arg1 = Integer.parseInt(mNumEdit.getText().toString());
			mThread.mBackHandler.sendMessage(msg);
			break;
		case R.id.root:
			msg = new Message();
			msg.what = 1;
			msg.arg1 = Integer.parseInt(mNumEdit.getText().toString());
			mThread.mBackHandler.sendMessage(msg);
			break;
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mBackText.setText("Square Result : " + msg.arg1);
				break;
			case 1:
				mBackText.setText("Root Result : " + ((Double)msg.obj).doubleValue());
				break;
			}
		}
	};
}

class CalcThread extends Thread {
	Handler mMainHandler;
	Handler mBackHandler;

	CalcThread(Handler handler) {
		mMainHandler = handler;
	}

	public void run() {
		Looper.prepare();
		mBackHandler = new Handler() {
			public void handleMessage(Message msg) {
				Message retmsg = new Message();
				switch (msg.what) {
				case 0:
					try { Thread.sleep(200); } catch (InterruptedException e) {;}
					retmsg.what = 0;
					retmsg.arg1 = msg.arg1 * msg.arg1;
					break;
				case 1:
					try { Thread.sleep(200); } catch (InterruptedException e) {;}
					retmsg.what = 1;
					retmsg.obj = new Double(Math.sqrt((double)msg.arg1));
					break;
				}
				mMainHandler.sendMessage(retmsg);
			}
		};
		Looper.loop();
	}
}
