package kr.co.infinity.HandlerTest;

import android.app.*;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

class WorkerThread extends Thread {
	Handler handler;

	WorkerThread(Handler handler) {
		this.handler = handler;
	}

	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			Message msg = new Message();
			msg.what = 1;
			msg.arg1 = i;
			handler.sendMessage(msg);
		}
	}
}

public class HandlerTest extends Activity {
	WorkerThread thread;
	TextView text;
	Handler handler;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		text = (TextView) findViewById(R.id.text);
		handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					text.setText("д╚©Нем=" + msg.arg1);
				}
			}
		};
		thread = new WorkerThread(handler);
		thread.start();
	}
}
