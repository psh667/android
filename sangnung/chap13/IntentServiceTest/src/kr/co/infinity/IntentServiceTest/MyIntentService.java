package kr.co.infinity.IntentServiceTest;

import android.content.Intent;
import android.util.Log;
import android.app.IntentService;

public class MyIntentService extends IntentService {

	/**
	 * A constructor is required, and must call the super constructor with a
	 * name for the worker thread.
	 */
	public MyIntentService() {
		super("MyIntentService");
	}

	/**
	 * The IntentService calls this method from the default worker thread with
	 * the intent that started the service. When this method returns,
	 * IntentService stops the service, as appropriate.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		// 여기서 시간이 많이 걸리는 작업을 진행하면 된다.
		// 예를 들어서 인터넷에서 파일을 다운로드 받을 수 있다.
		// 지금은 단순히 5초 동안 기다렸다가 리턴한다.
		long endTime = System.currentTimeMillis() + 5 * 1000;
		Log.i("SERVICE", "onHandleIntent() ");
		while (System.currentTimeMillis() < endTime) {
			synchronized (this) {
				try {
					wait(endTime - System.currentTimeMillis());
				} catch (Exception e) {
				}
			}
		}
	}

}