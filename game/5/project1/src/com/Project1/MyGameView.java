package com.Project1;

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

	final int LEFT = 1;		// 거미 이동 방향
	final int RIGHT = 2;
	
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
		Bitmap imgBack;			// 배경
		Bitmap imgSpider;		// 거미
		int sw, sh;				// 거미의 크기
		int mx, my;				// 거미의 좌표
		long lastTime;			// 시간 계산용
		
		ArrayList<Bubble> mBall = new ArrayList<Bubble>();			// 큰방울
		ArrayList<SmallBall> sBall = new ArrayList<SmallBall>();	// 작은방울
		ArrayList<WaterBall> wBall = new ArrayList<WaterBall>();	// 거미 총알
		
		boolean canRun = true;			// 스레드 제어용
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

			imgSpider = BitmapFactory.decodeResource(getResources(), R.drawable.spider1);
			sw = imgSpider.getWidth() / 2;
			sh = imgSpider.getHeight() / 2;
			
			mx = width / 2;
			my = height - 45;
		}
		
		//-------------------------------------
		//  큰 비눗방울 만들기 
		//-------------------------------------
		public void MakeBubble() {
			Random rnd = new Random();
			if (mBall.size() > 9 || rnd.nextInt(40) < 38) return;
			int x = -50;  
			int y = rnd.nextInt(201) + 50;	// 50~250 
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
		//  모든 캐릭터 이동  
		//-------------------------------------
		public void MoveCharacters() {
			// 큰 비눗방울
			for (int i = mBall.size() - 1; i >= 0; i--) {
				mBall.get(i).MoveBubble();
				if (mBall.get(i).dead == true) mBall.remove(i);  
			}	

			// 작은 비눗방울
			for (int i = sBall.size() - 1; i >= 0; i--) {
				sBall.get(i).MoveBall();
				if (sBall.get(i).dead == true)
					sBall.remove(i);
			}	
			// 거미 총알
			for (int i = wBall.size() - 1; i >= 0; i--) {
				wBall.get(i).MoveBall();
				if (wBall.get(i).dead == true)
					wBall.remove(i);
			}	
		}

		//-------------------------------------
		//  거미 이동 - KeyDown에서 호출
		//-------------------------------------
		private void MoveSpider(int n) {
			int sx = 4;
			if (n == LEFT) sx = -4; 
			mx += sx;
			if (mx < sw) mx = sw;					// 왼쪽 벽 
			if (mx > width - sw) mx = width - sw;	// 오른쪽 벽 
		}

		//-------------------------------------
		//  거미 총알 발사 - KeyDown에서 호출
		//-------------------------------------
		private void MakeWaterBall() {
			long thisTime = System.currentTimeMillis();
			if (thisTime - lastTime >= 300)		// 1/3초에 1개씩 발사
				wBall.add(new WaterBall(mContext, mx, my - 20, width, height));
			lastTime = thisTime;
		}

		//-------------------------------------
		//  충돌 판정
		//-------------------------------------
		public void CheckCollision() {
			int x1, y1, x2, y2;

			// 총알과 비눗방울의 충돌
			for (WaterBall water : wBall) {	// 총알
				x1 = water.x;
				y1 = water.y;
				for (Bubble tmp : mBall) {	// 비눗방울 
					x2 = tmp.x;
					y2 = tmp.y;
					if (Math.abs(x1 - x2) < tmp.rad && Math.abs(y1 - y2) < tmp.rad) {
						MakeSmallBall(tmp.x, tmp.y);	// 작은 풍선 만들고
						tmp.dead = true;				// 큰풍선 터뜨림
						water.dead = true;				// 총알도 없애고
						break;
					}
				} // for
			} // for	
		}

		//-------------------------------------
		//  Canvas에 그리기
		//-------------------------------------
		public void DrawCharacters(Canvas canvas) {
			canvas.drawBitmap(imgBack, 0, 0, null);		// 배경
			// 큰 비눗방울
			for (Bubble tmp : mBall)
				canvas.drawBitmap(tmp.imgBall, tmp.x - tmp.rad,  tmp.y - tmp.rad, null);
			// 작은비눗방울
			for (SmallBall tmp : sBall)
				canvas.drawBitmap(tmp.imgBall, tmp.x - tmp.rad,  tmp.y - tmp.rad, null);
			// 거미 물방울
			for (WaterBall tmp : wBall)
				canvas.drawBitmap(tmp.imgBall, tmp.x - tmp.rad,  tmp.y - tmp.rad, null);
			// 거미
			canvas.drawBitmap(imgSpider, mx - sw, my - sh, null);    
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
						MakeBubble();
						MoveCharacters();
						CheckCollision();
						DrawCharacters(canvas);
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

	//-------------------------------------
	//  onKeyDown
	//-------------------------------------
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		synchronized (mHolder) {
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_LEFT :
					mThread.MoveSpider(LEFT);
					break;
				case KeyEvent.KEYCODE_DPAD_RIGHT : 
					mThread.MoveSpider(RIGHT);
					break;
				case KeyEvent.KEYCODE_DPAD_UP : 
					mThread.MakeWaterBall();
				}
			}		
			return super.onKeyDown(keyCode, event);
		} // sync
	} // onKeyDown 
} // SurfaceView 
