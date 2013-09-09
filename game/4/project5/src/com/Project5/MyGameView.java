package com.Project5;

import java.util.*;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.view.SurfaceHolder.Callback;

public class MyGameView extends SurfaceView implements Callback {
	GameThread mThread;
	SurfaceHolder mHolder;
	Context mContext;
	
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
	    		mThread.join();        // 스레드가 현재 step을 끝낼 때 까지 대기
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
		SurfaceHolder mHolder;		// SurfaceHolder를 저장할 변수
		Context mContext;
		
		int width, height;
		Bitmap imgBack;
		ArrayList<Bubble> mBall = new ArrayList<Bubble>();			// 큰방울
		ArrayList<SmallBall> sBall = new ArrayList<SmallBall>();	// 작은방울
		
		boolean canRun = true;
		boolean isWait = false;
		  
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
		}
		
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
		
		//-------------------------------------
		//  비눗방울 만들기  - Touch Event에서 호출
		//-------------------------------------
		public void MakeBubble(int x, int y) {
			boolean flag = false;
			for (Bubble tmp :  mBall) {
				if (Math.pow(tmp.x - x, 2) + Math.pow(tmp.y - y, 2) 
						<= Math.pow(tmp.rad, 2)){
					tmp.dead = true;	// 비눗방울 Touch일 경우 
					flag = true;		
				}
			}
			if (flag == false)	 // 비눗방울 Touch가 아니면 비눗방울 생성 
				mBall.add(new Bubble(mContext, x, y, width, height));
		}

		//-------------------------------------
		//  작은  비눗방울 만들기
		//-------------------------------------
		private void MakeSmallBall(int x, int y) {
			Random rnd = new Random();
			int count = rnd.nextInt(9) + 7;   // 7~15개
			for (int i = 1; i <= count; i++) {
				int ang = rnd.nextInt(360);		 
				sBall.add(new SmallBall(mContext, x, y, ang, width, height));
			}
		}

		//-------------------------------------
		//  비눗방울 이동  - run에서 호출
		//-------------------------------------
		public void MoveBubble() {
			// 큰 비눗방울 이동
			for (int i = mBall.size() - 1; i >= 0; i--) {
				mBall.get(i).MoveBubble();
				if (mBall.get(i).dead == true) {
					// 작은 비눗방울을 만들고 큰 것은 터뜨림
					MakeSmallBall(mBall.get(i).x, mBall.get(i).y);	// 작은 방울
					mBall.remove(i);
				}
			}

			// 작은 비눗방울 이동
			for (int i = sBall.size() - 1; i >= 0; i--) {
				sBall.get(i).MoveBall();
				if (sBall.get(i).dead == true)
					sBall.remove(i);
			}
		}
		
		//-------------------------------------
		//  Canvas에 그리기
		//-------------------------------------
		public void run() {
			Canvas canvas = null; 					// canvas를 만든다
			while (canRun) {
				canvas = mHolder.lockCanvas();		// canvas를 잠그고 버퍼 할당
				try {
					synchronized (mHolder) {		// 동기화 유지
						MoveBubble();
						canvas.drawBitmap(imgBack, 0, 0, null);

						// 큰 비눗방울
						for (Bubble tmp : mBall) {
							canvas.drawBitmap(tmp.imgBall, tmp.x - tmp.rad,  tmp.y - tmp.rad, null);
						}	
						// 작은비눗방울
						for (SmallBall tmp : sBall) {
							canvas.drawBitmap(tmp.imgBall, tmp.x - tmp.rad,  tmp.y - tmp.rad, null);
						}	
					}
				} finally {							// 버퍼 작업이 끝나면 
					if (canvas != null)				// 버퍼의 내용을 View에 전송
						mHolder.unlockCanvasAndPost(canvas);
				} // try

				// 스레드 일시 정지 
				synchronized (this) {
            		if (isWait)
            			try {
            				wait();
            			} catch (Exception e) {
							// nothing
						}
    			} // sync
				
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
	
} // SurfaceView 
