package com.Project_1;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.view.SurfaceHolder.Callback;

public class MyGameView extends SurfaceView implements Callback {

	//-------------------------------------
	//  생성자
	//-------------------------------------
	public MyGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//-------------------------------------
    //  SurfaceView가 생성될 때 실행되는 부분
    //-------------------------------------
	@Override
	public void surfaceCreated(SurfaceHolder holder) {

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

	}
	
//------------------------------------------------------
	
	//-------------------------------------
	//  GameThread Class
	//-------------------------------------
	class GameThread extends Thread {
		SurfaceHolder mHolder;		// SurfaceHolder를 저장할 변수 
		  
		//-------------------------------------
		//  생성자 
		//-------------------------------------
		public GameThread(SurfaceHolder holder, Context context) {
			mHolder = holder;	// SurfaceHolder 보존
		}
		
		//-------------------------------------
		//  run Callback 함수 
		//-------------------------------------
		public void run() {
			Canvas canvas = null; 					// canvas를 만든다
			while (true) {
				canvas = mHolder.lockCanvas();		// canvas를 잠그고 버퍼 할당
				try {
					synchronized (mHolder) {		// 동기화 유지
						// 실제 그래픽 처리 부분
					}
				} finally {							// 버퍼 작업이 끝나면 
					if (canvas != null)				// 버퍼의 내용을 View에 전송
						mHolder.unlockCanvasAndPost(canvas);     
				}
			} // while
		} // run
	} // GameThread 끝
	
} // SurfaceView 
