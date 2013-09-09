package com.Project3;

import java.util.*;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.view.SurfaceHolder.Callback;

public class MyGameView extends SurfaceView implements Callback {
	GameThread mThread;
	SurfaceHolder mHolder;

	//-------------------------------------
	//  생성자
	//-------------------------------------
	public MyGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		mHolder = holder;
		mThread = new GameThread(holder, context); 
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
	    		mThread.join();        // 스레드가 현재 step을 끝낼 때 까지 대기
	    		done = false;
	 		} catch (InterruptedException e) {  // 인터럽트 신호가 오면?   
	 			// 그 신호 무시 - 아무것도 않음
	 		} 
	    } // while
	}
	
//----------------------------------------------------------------
	
	//-------------------------------------
	//  GameThread Class
	//-------------------------------------
	class GameThread extends Thread {
		SurfaceHolder mHolder;		// SurfaceHolder를 저장할 변수
		Context mContext;
		
		int width, height;
		Bitmap imgBack;
		ArrayList<Bubble> mBall = new ArrayList<Bubble>();
		  
		//-------------------------------------
		//  생성자 
		//-------------------------------------
		public GameThread(SurfaceHolder holder, Context context) {
			mHolder = holder;	// SurfaceHolder 보존
			mContext = context;
			
			Display display = ((WindowManager) context.getSystemService (Context.WINDOW_SERVICE)).getDefaultDisplay();
			width = display.getWidth();		// View의 가로 폭
			height = display.getHeight() - 50;   // View의 세로 높이

			imgBack = BitmapFactory.decodeResource(getResources(), R.drawable.sky);
			imgBack = Bitmap.createScaledBitmap(imgBack, width, height, false);

			setFocusable(true);
		}
		
		//-------------------------------------
		//  풍선 만들기  - Touch Event에서 호출
		//-------------------------------------
		public void MakeBubble(int x, int y) {
			boolean flag = false;
			for (Bubble tmp :  mBall) {
				if (Math.pow(tmp.x - x, 2) + Math.pow(tmp.y - y, 2) 
						<= Math.pow(tmp.rad, 2)){
					tmp.dead = true;	// 비누방울 Touch일 경우 
					flag = true;		
				}
			}
			if (flag == false)	 // 비누방울 Touch가 아니면 비누방울 생성 
				mBall.add(new Bubble(mContext, x, y, width, height));
		}

		//-------------------------------------
		//  풍선 이동  - run에서 호출
		//-------------------------------------
		public void MoveBubble() {
			for (int i = mBall.size() - 1; i >= 0; i--) {
				mBall.get(i).MoveBubble();
				if (mBall.get(i).dead == true)
					mBall.remove(i);
			}
		}
		//-------------------------------------
		//  Canvas에 그리기
		//-------------------------------------
		public void run() {
			Canvas canvas = null; 					// canvas를 만든다
			while (true) {
				canvas = mHolder.lockCanvas();		// canvas를 잠그고 버퍼 할당
				try {
					synchronized (mHolder) {		// 동기화 유지
						MoveBubble();
						canvas.drawBitmap(imgBack, 0, 0, null);
						for (Bubble tmp : mBall) {
							canvas.drawBitmap(tmp.imgBall, tmp.x - tmp.rad,  tmp.y - tmp.rad, null);
						}	
					}
				} finally {							// 버퍼 작업이 끝나면 
					if (canvas != null)				// 버퍼의 내용을 View에 전송
						mHolder.unlockCanvasAndPost(canvas);     
				}
			} // while
		} // run
	
	} // GameThread 끝
	
	//-------------------------------------
	//  onTouch Event 
	//-------------------------------------
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (mHolder) {
				int x = (int) event.getX(); 
				int y = (int) event.getY();
				mThread.MakeBubble(x, y);
			}
		}
		return true;
	}
	
	//----------------------
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.exit(0);
		return true;
	}

} // SurfaceView 
