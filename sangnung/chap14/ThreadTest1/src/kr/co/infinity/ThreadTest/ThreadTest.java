package kr.co.infinity.ThreadTest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ThreadTest extends Activity {
	Thread w;
	boolean running = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onStart() {
		super.onStart();
		w = new Thread(new Runnable() {
			public void run() {
				int i = 0;
				for (i = 0; i < 20 && running; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					Log.v("THREAD", "time=" + i);
				}
			}
		});
		running = true;
		w.start();
	}

	@Override
	public void onStop() {
		super.onStop();
		running = false;
	}
}