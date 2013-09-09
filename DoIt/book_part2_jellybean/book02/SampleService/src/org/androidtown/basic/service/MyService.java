package org.androidtown.basic.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 서비스 클래스 정의
 * 
 * @author Mike
 */
public class MyService extends Service implements Runnable {
	/**
	 * 디버깅을 위한 태그
	 */
	public static final String TAG = "MyService";
	
	/**
	 * 반복 횟수
	 */
	private int count = 0;
	
	/**
	 * 서비스 객체 생성 시 자동 호출됩니다.
	 */
	public void onCreate() {
		super.onCreate();
		
		// 스레드를 이용해 반복하여 로그를 출력합니다.
		Thread myThread = new Thread(this);
		myThread.start();
	}

	/**
	 * 스레드의 실행 부분
	 */
	public void run() {
		while(true) {
			try {
				Log.i(TAG, "my service called #" + count);
				count++;
				
				Thread.sleep(5000);
			} catch(Exception ex) {
				Log.e(TAG, ex.toString());
			}
		}
		
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
