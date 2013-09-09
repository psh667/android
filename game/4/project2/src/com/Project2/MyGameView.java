package com.Project2;

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

		setFocusable(true);		// View가 포커스를 받을 수 있도록 설정
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
	
//-------------------------------------------------------------------
	
	//-------------------------------------
	//  GameThread Class
	//-------------------------------------
	class GameThread extends Thread {
		SurfaceHolder mHolder;		// SurfaceHolder 보존

		int width, height;
		int x, y, dw, dh;			// 용의 좌표, 크기
		int sx, sy;					// 용의 방향과 속도
		int num;					// 이미지 번호
		int x1, y1;					// 직전에 Touch 한 위치
		Bitmap imgBack;				// 배경이미지
		Bitmap Dragon[] = new Bitmap[2];   // 용 - 좌우 이미지
		
		//-------------------------------------
		//  생성자 
		//-------------------------------------
		public GameThread(SurfaceHolder holder, Context context) {
			mHolder = holder;	// SurfaceHolder 보존
			Display display = ((WindowManager) context.getSystemService (Context.WINDOW_SERVICE)).getDefaultDisplay();
			width = display.getWidth();		// View의 가로 폭
			height = display.getHeight() - 50;   // View의 세로 높이

			// 배경 읽고 늘리기
			imgBack = BitmapFactory.decodeResource(getResources(), R.drawable.back_1);
			imgBack = Bitmap.createScaledBitmap(imgBack, width, height, false);

			Dragon[0] = BitmapFactory.decodeResource(getResources(), R.drawable.dragon);
			dw = Dragon[0].getWidth() / 2;		// 용의 폭
			dh = Dragon[0].getHeight() / 2;		// 높이
			
			Matrix matrix = new Matrix();		// 이미지를 뒤집기 위해 Matrix 생성
			matrix.postScale(-1, 1);			// 수평으로 뒤집기
			Dragon[1] = Bitmap.createBitmap(Dragon[0], 0, 0, dw * 2, dh * 2, matrix, false);
			
			//용의 초기값 설정
			num = 0;			// 이미지 번호
			sx = -2;
			sy = 3;
			x = 100;
			y = 100;
		}
		
		//-------------------------------------
		//  용 움직이기 
		//-------------------------------------
		public void MoveDragon() {
			x += sx;
			y += sy;
			if (x <= dw || x >= width - dw) {
				sx = -sx;
				num = 1 - num;		// 0, 1, 0, 1, ...
			}
			if (y <= dh || y >= height - dh) {
				sy = -sy;
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
						MoveDragon();							// 용 움직이기
						canvas.drawBitmap(imgBack, 0, 0, null);	// 배경
						canvas.drawBitmap(Dragon[num], x - dw, y - dh, null); // 용
					}
				} finally {							// 버퍼 작업이 끝나면 
					if (canvas != null)				// 버퍼의 내용을 View에 전송
						mHolder.unlockCanvasAndPost(canvas);     
				}
			} // while
		} // run
	} // GameThread 끝

//----------------------------------------------------------
	
	//-------------------------------------
	//  onTouch Event 
	//-------------------------------------
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mThread.x1 = (int) event.getX();
			mThread.y1 = (int) event.getY();
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			int x2 = (int) event.getX();
			int y2 = (int) event.getY();
			
			mThread.sx = (x2 - mThread.x1) / 10;
			mThread.sy = (y2 - mThread.y1) / 10;
			if (mThread.x1 < x2) 
				mThread.num = 1;
			else
				mThread.num = 0;
		}
		return true;
	} // TouchEvent 
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.exit(0);
		return true;
	}

} // SurfaceView 
