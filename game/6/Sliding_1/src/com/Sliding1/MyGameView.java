package com.Sliding1;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.view.SurfaceHolder.Callback;

public class MyGameView extends SurfaceView implements Callback {

	GameThread 	  mThread;		// GameThread
	SurfaceHolder mHolder;		// SurfaceHolder 
	Context 	  mContext;		// Context 

	//-------------------------------------
	//  생성자
	//-------------------------------------
	public MyGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		
		mHolder = holder;		// holder와 Context 보존
		mContext = context;
		mThread = new GameThread(holder, context);

		setFocusable(true);
	}
	
	//-------------------------------------
    //  SurfaceView가 생성될 때 실행되는 부분
    //-------------------------------------
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mThread.start();
	}

	//-------------------------------------
    //  SurfaceView가 바뀔 때 실행되는 부분
    //-------------------------------------
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {

	}

	//-------------------------------------
    //  SurfaceView가 해제될 때 실행되는 부분
    //-------------------------------------
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean done = true;
	    while (done) {
	    	try {
	    		mThread.join();        			// 스레드가 현재 step을 끝낼 때 까지 대기
	    		done = false;
	 		} catch (InterruptedException e) {  // 인터럽트 신호가 오면?   
	 			// 그 신호 무시 - 아무것도 않음
	 		} 
	    } // while
	}
	
	//-------------------------------------
	//  스레드 완전 정지
	//-------------------------------------
	public void StopGame() {
		mThread.StopThread(); 
	}

	//-------------------------------------
	//  스레드 일시 정지
	//-------------------------------------
	public void PauseGame() {
		mThread.PauseNResume(true); 
	}

	//-------------------------------------
	//  스레드 재기동
	//-------------------------------------
	public void ResumeGame() {
		mThread.PauseNResume(false); 
	}

	//-------------------------------------
	//  게임 초기화
	//-------------------------------------
	public void RestartGame() {
		mThread.StopThread();		// 스레드 중지

		// 현재의 스레드를 비우고 다시 생성
	    mThread = null;	  
		mThread = new GameThread(mHolder, mContext); 
		mThread.start(); 
	}

//----------------------------------------------------------------
	
	//-------------------------------------
	//  GameThread Class
	//-------------------------------------
	class GameThread extends Thread {
		boolean canRun = true;			// 스레드 제어용
		boolean isWait = false;
			
		//-------------------------------------
		//  생성자 
		//-------------------------------------
		public GameThread(SurfaceHolder holder, Context context) {
			
		}
		
		//-------------------------------------
		//  스레드 본체
		//-------------------------------------
		public void run() {
			Canvas canvas = null; 					// canvas를 만든다
			while (canRun) {
				canvas = mHolder.lockCanvas();		// canvas를 잠그고 버퍼 할당
				try {
					synchronized (mHolder) {		// 동기화 유지
						
					}
				} finally {							// 버퍼 작업이 끝나면 
					if (canvas != null)				// 버퍼의 내용을 View에 전송
						mHolder.unlockCanvasAndPost(canvas);
				} // try

				// 스레드 일시 정지 
				synchronized (this) {
            		if (isWait)				// Pause 모드이면
            			try {
            				wait();			// 스레드 대기
            			} catch (Exception e) {
							// nothing
						}
    			} // sync
				
			} // while
		} // run
	
		//-------------------------------------
		//  스레드 완전 정지
		//-------------------------------------
		public void StopThread() {
			canRun = false;
        	synchronized (this) {
        		this.notify();
			}
		}
		
		//-------------------------------------
		//  스레드 일시정지 / 재기동
		//-------------------------------------
		public void PauseNResume(boolean wait) { 
			isWait = wait;
        	synchronized (this) {
        		this.notify();
			}
		}
	} // GameThread 끝
} // SurfaceView 
