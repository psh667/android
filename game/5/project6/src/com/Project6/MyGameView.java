package com.Project6;

import java.util.*;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.view.SurfaceHolder.Callback;

public class MyGameView extends SurfaceView implements Callback {
	GameThread 	  mThread;
	SurfaceHolder mHolder;
	Context 	  mContext;

	final int LEFT = 1;
	final int RIGHT = 2;
	final int STOP = 3;
	
	static int B_width;		// 블록의 폭
	static int B_height;	// 블록의 높이
	static int M_left;		// 좌측 여백
	static int M_top;		// 상단 여백
	
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
		SurfaceHolder mHolder;			// SurfaceHolder를 저장할 변수
		Context mContext;
		boolean canRun = true;			// 스레드 제어용
		boolean isWait = false;
		int width, height;				// 화면의 크기
		
		final int STAGE_COUNTER = 4;	// 전체 스테이지 갯수
		int ballCnt = 4;				// 볼의 갯수(0~5)
		int stgNum = 0;					// 스테이지 번호
		int tot = 0;					// 득점 합계
		int score = 0;					// 격파한 블록 갯수
		int sx[] = {-3, -2, 2, 3};		// 패들에 충돌 후의 볼의 반사 방향을 바꿈
		Random rnd = new Random();		// 위의 처리를 하기 위한 난수
		Paint paint = new Paint();		// 점수 표시용
		Bitmap imgBack;					// 배경
		Bitmap imgSball;				// 남은 공 갯수 표시용
		
		ArrayList<Block> mBlock = new ArrayList<Block>();	// 블록
		Ball   mBall;		// 볼
		Paddle mPaddle;		// 패들							
		  
		float Stage[][][] = {		// x, y, 블록번호
		{ 	
			{0, 0, 0}, {1, 0, 0}, {2, 0, 0}, {3, 0, 0}, {4, 0, 0},
			{0, 1, 0}, {1, 1, 1}, {2, 1, 1}, {3, 1, 1}, {4, 1, 0},
			{0, 2, 0}, {1, 2, 1}, {2, 2, 2}, {3, 2, 1}, {4, 2, 0},
			{0, 3, 0}, {1, 3, 1}, {2, 3, 2}, {3, 3, 1}, {4, 3, 0},
			{0, 4, 0}, {1, 4, 1}, {2, 4, 1}, {3, 4, 1}, {4, 4, 0},
			{0, 5, 0}, {1, 5, 0}, {2, 5, 0}, {3, 5, 0}, {4, 5, 0}
		},	
		
		{	{1.5f, 0, 0}, {2.5f, 0, 0}, 
			{1, 1, 0},    {2, 1, 1},    {3, 1, 0},
			{0.5f, 2, 0}, {1.5f, 2, 1}, {2.5f, 2, 1}, {3.5f, 2, 0},
			{0, 3, 0},    {1, 3, 1},    {2, 3, 2},    {3, 3, 1},    {4, 3, 0},
			{0.5f, 4, 0}, {1.5f, 4, 1}, {2.5f, 4, 1}, {3.5f, 4, 0},
			{1, 5, 0},    {2, 5, 1},    {3, 5, 0},
			{1.5f, 6, 0}, {2.5f, 6, 0}  
  		},	

  		{	{-0.5f, 0, 0}, {0.5f, 0, 0}, {1.5f, 0, 0}, {2.5f, 0, 0}, {3.5f, 0, 0}, {4.5f, 0, 0},
  			{-0.5f, 1, 0}, {4.5f, 1, 0},
  			{-0.5f, 2, 0}, {1, 2, 1}, {2, 2, 1}, {3, 2, 1}, {4.5f, 2, 0},
  			{-0.5f, 3, 0}, {1, 3, 1}, {2, 3, 2}, {3, 3, 1}, {4.5f, 3, 0},
  			{-0.5f, 4, 0}, {1, 4, 1}, {2, 4, 1}, {3, 4, 1}, {4.5f, 4, 0},
  			{-0.5f, 5, 0}, {4.5f, 5, 0},
 	  		{-0.5f, 6, 0}, {0.5f, 6, 0}, {1.5f, 6, 0}, {2.5f, 6, 0}, {3.5f, 6, 0}, {4.5f, 6, 0},
  		},
  		
  		{	{-0.5f, 0, 0}, {0.5f, 0, 0}, {1.5f, 0, 0}, {2.5f, 0, 0}, {3.5f, 0, 0}, {4.5f, 0, 0},
			{0, 1, 0},     {1, 1, 1},    {2, 1, 1},    {3, 1, 1},    {4, 1, 0},
			{0.5f, 2, 0}, {1.5f, 2, 1}, {2.5f, 2, 1},  {3.5f, 2, 0},
			{1, 3, 0},    {2, 3, 0},    {3, 3, 0},
			{1.5f, 4, 2}, {2.5f, 4, 2}, 
			{1, 5, 0},    {2, 5, 0},    {3, 5, 0},
			{0.5f, 6, 0}, {1.5f, 6, 1}, {2.5f, 6, 1},  {3.5f, 6, 0},
			{0, 7, 0},     {1, 7, 1},    {2, 7, 1},    {3, 7, 1},    {4, 7, 0},
			{-0.5f, 8, 0}, {0.5f, 8, 0}, {1.5f, 8, 0}, {2.5f, 8, 0}, {3.5f, 8, 0}, {4.5f, 8, 0},
  		}	
		};
			
		//-------------------------------------
		//  생성자 
		//-------------------------------------
		public GameThread(SurfaceHolder holder, Context context) {
			mHolder = holder;					// SurfaceHolder 보존
			mContext = context;
			
			Display display = ((WindowManager) context.getSystemService (Context.WINDOW_SERVICE)).getDefaultDisplay();
			width = display.getWidth();			// View의 가로 폭
			height = display.getHeight() - 50;  // View의 세로 높이

			InitGame();
			MakeStage();
		}
		
		//-------------------------------------
		//  게임 초기화 
		//-------------------------------------
		public void InitGame() {
			B_width = width / 6;					// 블록의 폭
			B_height = B_width / 2;					// 블록의 폭
			M_left = (width - B_width * 5) / 2;		// 왼쪽 여백
			M_top = B_width * 4 / 5;				// 상단 여백 

			mPaddle = new Paddle(mContext, width / 2, height - B_height, width);
			mBall = new Ball(mContext, width / 2, mPaddle.y - 17, width, height);
			
			paint.setAntiAlias(true);
			paint.setColor(Color.WHITE);
			paint.setTextSize(15);
			
			// 남은 볼 갯수 표시용
			imgSball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
			imgSball = Bitmap.createScaledBitmap(imgSball, 10, 14, false);
		}
		//-------------------------------------
		//  Stage 만들기 
		//-------------------------------------
		public void MakeStage() {
			for (int i = 0; i < Stage[stgNum].length; i++)
				mBlock.add(new Block(mContext, Stage[stgNum][i][0], Stage[stgNum][i][1], Stage[stgNum][i][2]));
			ResetPosition();			// 패들과 볼을 화면 중심으로 이동
			mBall.sy = -(4 + stgNum);	// 스테이지마다 1씩 속도 증가		 
			
			// 배경
			imgBack = BitmapFactory.decodeResource(getResources(), R.drawable.back0 + stgNum);
			imgBack = Bitmap.createScaledBitmap(imgBack, width, height, false);
		}

		//-------------------------------------
		//  공과 패들을 초기 위치로 이동 - 공을 잃은 후 
		//-------------------------------------
		public void ResetPosition() {
			mPaddle.x = width / 2;
			mPaddle.y = height - B_height;
			mPaddle.sx = 0;

			mBall.x = width / 2;
			mBall.y = mPaddle.y - 17;
			mBall.sy = -Math.abs(mBall.sy); 
			mBall.isMove = false;
		}

		//-------------------------------------
		//  패들 이동 - Key Event에서 호출
		//-------------------------------------
		public void MovePaddle(int direction) {
			switch (direction) {
			case LEFT :
				mPaddle.sx = -4;
				break;
			case RIGHT :
				mPaddle.sx = 4;
				break;
			case STOP :
				mPaddle.sx = 0;
			}
		}
		
		//-------------------------------------
		//  Ball 발사  - Key Event에서 호출
		//-------------------------------------
		public void ShootBall() {
			mBall.isMove = true;
		}
		
		//-------------------------------------
		//  Ball과 패들 이동 - run()에서 호출
		//-------------------------------------
		public void MoveBall() {
			mPaddle.Move();
			if (mBall.isMove == false)	// 초기 모드는
				mBall.x = mPaddle.x;	// 패들과 볼이 같이 이동
			if (mBall.Move() == false) {
				ballCnt--;
				if (ballCnt < 0) {		// 볼을 모두 잃으면 게임 오버
					GameOver();
					return;
				}
				ResetPosition();		// 패들, 공 초기 위치로
			}
		}
		//-------------------------------------
		//  충돌 판정 - run()에서 호출 
		//-------------------------------------
		public void CheckCollision() {
			if (mBlock.size() == 0) {			// 이 판을 클리어 했으면
				stgNum++;						// 다음 스테이지로
				if (stgNum >= STAGE_COUNTER)	// 스테이지 끝은 처음 스테이지 
					stgNum = 0;
				MakeStage();					// 새로운 스테이지 만들기
				return;
			}

			// 패들과 충돌
			if (Math.abs(mBall.x - mPaddle.x) <= mPaddle.pw 
					&& mBall.y >= (mPaddle.y - 17) && mBall.y < mPaddle.y) {
				mBall.sx = sx[rnd.nextInt(4)];		// 난수로 각도 지정
				mBall.sy = -Math.abs(mBall.sy);		// 무조건 반사 
			}
			
			// 블록과 충돌
			for (Block tmp : mBlock) {
				// 충돌 없음
				if (mBall.x + mBall.bw < tmp.x1 || mBall.x - mBall.bw > tmp.x2		 
						|| mBall.y + mBall.bw < tmp.y1 || mBall.y - mBall.bw > tmp.y2) {
					continue;
				}	
				// 양쪽 벽과의 충돌인지 판정
				if (tmp.x1 - mBall.x >= mBall.bw || mBall.x - tmp.x2 >= mBall.bw)
					mBall.sx = - mBall.sx;
				else											// 상하 충돌
					mBall.sy = - mBall.sy;
				tot += tmp.score;								// 득점
				score++;
				mBlock.remove(tmp);								// 블록 제거
				break;
			}
		}

		//-------------------------------------
		//  Canvas에 그리기 - run()에서 호출
		//-------------------------------------
		public void DrawCharacters(Canvas canvas) {
			// 배경
			canvas.drawBitmap(imgBack, 0, 0, null);
			// 남은 공 수
			for (int i = 0; i <= ballCnt; i++) 
				canvas.drawBitmap(imgSball, i * 12 + 5, height - 20, null); 
			// 블록
			for (Block tmp : mBlock) 
				canvas.drawBitmap(tmp.imgBlk, tmp.x1, tmp.y1, null);
			// Ball
			canvas.drawBitmap(mBall.imgBall, mBall.x - mBall.bw, mBall.y- mBall.bh, null);
			// Paddle
			canvas.drawBitmap(mPaddle.imgPdl, mPaddle.x - mPaddle.pw , mPaddle.y - mPaddle.ph, null);
			// 점수
			canvas.drawText("Stage" + stgNum, 5, 18, paint);
			canvas.drawText("블록 : " + score, width / 2 - 40, 18, paint);
			canvas.drawText("득점 : " + tot, width - 80, 18, paint);
		}
		
		//-------------------------------------
		//  Game Over
		//-------------------------------------
		private void GameOver() {
			// 게임 오버는 여기에서 처리힌다
			// 아직 게임 오버 없음
			ballCnt = 4;	// 볼의 갯수를 다시 4개로...
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
						MoveBall();					// 볼 이동하고
						CheckCollision();			// 충돌 판정 후
						DrawCharacters(canvas);		// Canvas에 그리기
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

	//-------------------------------------
	//  onKeyDown
	//-------------------------------------
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		synchronized (mHolder) {
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_LEFT:
					mThread.MovePaddle(LEFT);		// 패들을 왼쪽으로
					break;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					mThread.MovePaddle(RIGHT);		// 패들을 오른쪽으로
					break;
				case KeyEvent.KEYCODE_DPAD_DOWN:
					mThread.MovePaddle(STOP);		// 패들 정지
					break;
				case KeyEvent.KEYCODE_DPAD_UP:		// 볼 발사
					mThread.ShootBall();
				}
			}		
			return super.onKeyDown(keyCode, event);
		} // sync
	} // onKeyDown 
} // SurfaceView 
