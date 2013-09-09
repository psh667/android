package com.StarWars;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MyGameView extends SurfaceView implements Callback {
	
	// 디버그에 관한 상수
	boolean DEBUG = false;						// 디버그 용

	// 프로그램 상태에 관한 상수	
	final static int PROCESS = 1;				// 게임 중
	final static int STAGE_CLEAR = 2;			// Stage Clear
	final static int GAMEOVER = 3;				// Game Over
	final static int ALL_CLEAR = 4;				// All Clear
	
	// 전체 스테이지 수와 보스 출현 빈도 상수 
	final static int MAX_STAGE = 6;				// 전체 스테이지 수
	final static int BOSS_COUNT = 3;			// Boss 출현 빈도

	// 게임 난이도 - 메인 메뉴용
	final static int EASY = 0;					// 게임 난이도
	final static int MEDIUM = 1;
	final static int HARD = 2;
	static int difficult = EASY;				// 게임 난이도
	
	// SurfaceView의 변수들
	static GameThread mThread;					// GameThread
	static SurfaceHolder mHolder;				// SurfaceHolder 
	static Context mContext;					// Context 

	// 메인 메뉴용 변수들
	static boolean isMusic = true;				// 배경 음악
	static boolean isSound = true;				// 효과음
	static boolean isVibe = true;				// 진동
	
	// Class 변수들
	static MapTable mMap;						// 맵 테이블
	static GunShip mShip;						// 아군 우주선
	static EnemyBoss mBoss;						// 적군 Boss
	static ArrayList<Missile> mMissile;			// 적군 미사일 
	static ArrayList<FireGun> mGun; 			// 아군 미사일  
	static ArrayList<Explosion> mExp;			// 폭파 불꽃 
	static ArrayList<Bonus> mBonus;	 			// Bonus
	static ArrayList<BossMissile> mBsMissile;	// Boss Missile 
	static Sprite mEnemy[][] = new Sprite[6][8];// 적군
	static AttackEnemy mAttack;					// 적군 공격 Class 
	Collision mCollision;						// 충돌 판정 Class
	static GameOver mGameOver; 					// 게임오버&All Clear Class
	StageClear mClear;							// Stage Clear Class 

	// Game의 변수들
	static int Width, Height;					// View
	static int stageNum;	  					// 스테이지 번호 
	static int shipCnt = 3;						// 남은 우주선 수
	static int score = 0;						// 점수
	static int gunDelay = 15;					// 미사일 발사 지연 시간

	// Game 진행에 관한  flag 변수들
	static boolean isPower = false;				// 강화된 미사일
	static boolean isDouble = false;				// 미사일 2개씩 발사
	static boolean isAutoFire = false;			// 미사일 자동 발사
	static boolean isBoss = false;				// 보스 출현
	static int status = PROCESS;				// 현재 진행 상태

	// 비트맵에 관한 변수들
	static Bitmap imgBack;						// 배경 이미지
	static int sw[] = new int[6];				// 적군의 폭과 높이 
	static int sh[] = new int[6];
	Bitmap imgMiniShip;							// 남은 우주선 수
	
	// 사운드 관련 변수들
	static SoundPool sdPool;
	static int sdFire, sdExp0, sdExp1, sdExp2, sdExp3;
	static Vibrator vibe;
	static MediaPlayer player;
        
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
		
		InitGame();				// 게임 초기화
		MakeStage();			// 스테이지 만들기
		setFocusable(true);		// View가 Focus받기
	}
	
	//-------------------------------------
    //  InitGame
    //-------------------------------------
	private void InitGame() {
		// 화면 해상도 구하기
		Display display = ((WindowManager) mContext.getSystemService (Context.WINDOW_SERVICE)).getDefaultDisplay();
		Width = display.getWidth();			
		Height = display.getHeight();
		
		// Class 변수 초기화		
		mMap = new MapTable();						// 맵 테이블
		mAttack = new AttackEnemy();				// 적군 공격 Class
		mMissile = new ArrayList<Missile>();		// 적군 미사일	 
		mGun = new ArrayList<FireGun>();			// 아군 미사일
		mBsMissile = new ArrayList<BossMissile>();	// Boss Missile
		mExp = new ArrayList<Explosion>();			// 폭파 불꽃
		mBonus = new ArrayList<Bonus>();			// Bonus
		mCollision = new Collision();				// 충돌 판정 Class
		
		mClear = new StageClear();					// 스테이지 끝 처리용 
		mGameOver = new GameOver();					// 게임 오버 처리용
		mBoss = new EnemyBoss();					// Boss

		// 아군기 설정과 게임 상태
		mShip = new GunShip(Width / 2, Height - 60);// 우주선	
		shipCnt = 3;								// 남은 우주선 수
		stageNum = 1;  								// 스테이지 번호 
		status = PROCESS;							// 현재 진행 상태
		
		// 적군 캐릭터 배열
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				mEnemy[i][j] = new Sprite();
			}
		}
		
		// Score 표시용 Minimap
		imgMiniShip = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.miniship);
		
		// Options Menu 설정값 읽기
		difficult = ((GlobalVars) mContext.getApplicationContext()).getDifficult();
		isMusic = ((GlobalVars) mContext.getApplicationContext()).getIsMusic();
		isSound = ((GlobalVars) mContext.getApplicationContext()).getIsSound();
		isVibe = ((GlobalVars) mContext.getApplicationContext()).getIsVibe();
		
		// 효과음
		sdPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    sdFire = sdPool.load(mContext, R.raw.fire, 1);
	    sdExp0 = sdPool.load(mContext, R.raw.exp0, 2);
	    sdExp1 = sdPool.load(mContext, R.raw.exp1, 3);
	    sdExp2 = sdPool.load(mContext, R.raw.exp2, 4);
	    sdExp3 = sdPool.load(mContext, R.raw.exp3, 5);
	    
	    // 진동
	    vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

	    // 배경 음악
  	    player = MediaPlayer.create(mContext, R.raw.green);   	// 파일 읽기 green은 파일명
	    player.setVolume(0.7f, 0.7f); 			      			// 볼륨 설정
	    player.setLooping(true);              					// 반복 연주
	    
	    if (isMusic) player.start();
	} 
	
	//-------------------------------------
    //  MakeStage
    //-------------------------------------
	public static void MakeStage() {
		mMap.ReadMap(stageNum);
		imgBack = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.space0 + stageNum % 5 - 1);
		imgBack = Bitmap.createScaledBitmap(imgBack, Width, Height, true);

		// 배열에 적군 캐릭터 만들기
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				mEnemy[i][j].MakeSprite(i, j);
			}
			sw[i] = mEnemy[i][2].w;	
			sh[i] = mEnemy[i][2].h;
		}

		mShip.y = Height - 36;				// 아군기 위치
		mAttack.ResetAttack();				// 적기 공격시간 초기화
	}
	
	//-------------------------------------
    //  Make Boss Stage
    //-------------------------------------
	public static void MakeBossStage() {
		// 적군 캐릭터 복원
		for (int i = 2; i <= 4; i++) {
			for (int j = 0; j < 8; j++) {
				mEnemy[i][j].ResetSprite();
			}
		}

		mMap.enemyCnt = 24;			// 적 카운터
		mBoss.InitBoss();			// Boss 초기화
		isBoss = true;				// Thread에 통지
		status = PROCESS;			// Thread에 통지
		mShip.y = Height - 36;		// 아군기 위치
		mAttack.ResetAttack();		// 적군 공격 시간 초기화
	}
	
	//-------------------------------------
    //  QuitGame - 게임끝내고 StartGame으로 복귀
    //-------------------------------------
	public static void GameOver() {
		StopGame();				// Thread 정지
		// StartGame Activity 실행
		mContext.startActivity(new Intent(mContext, StartGame.class));
		// 자신(MainActuivity)은 종료
		((Activity) mContext).finish(); 		
	}
	
	//-------------------------------------
    //  SurfaceView가 생성될 때 실행되는 부분
    //-------------------------------------
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mThread.start();
		} catch (Exception e) {
			RestartGame();
			if (isMusic) player.start();
		}
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
		StopGame();
		player.stop();
	}
	
	//-------------------------------------
	//  스레드 완전 정지
	//-------------------------------------
	public static void StopGame() {
		mThread.StopThread(); 
	}

	//-------------------------------------
	//  스레드 일시 정지
	//-------------------------------------
	public static void PauseGame() {
		mThread.PauseNResume(true); 
	}

	//-------------------------------------
	//  스레드 재기동
	//-------------------------------------
	public static void ResumeGame() {
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
		boolean canRun = true;			// Thread 제어용
		boolean isWait = false;			// Thread 제어용
		int loop;						// 루프 카운터 - 아군 미사일 발사 간격 제어용
		Paint paint = new Paint();		// Score 표시용 
		
		//-------------------------------------
		//  생성자 
		//-------------------------------------
		public GameThread(SurfaceHolder holder, Context context) {
			// Score 표시용 
			paint.setColor(Color.WHITE);
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			paint.setTypeface(Typeface.create("", Typeface.BOLD));
		}
		
		//-------------------------------------
		//  충돌 판정
		//-------------------------------------
		public void CheckCollision() {
			mCollision.CheckCollision();
		}
		
		//-------------------------------------
		//  적기 공격
		//-------------------------------------
		public void AttackSprite() {
			mAttack.Attack();
		}
		
		//-------------------------------------
		//  아군 미사일 발사
		//-------------------------------------
		public void FireGunship() {
			if (loop < gunDelay || mShip.isDead) return;

			if (isDouble) {
				mGun.add(new FireGun(mShip.x - 18, mShip.y));
				mGun.add(new FireGun(mShip.x + 18, mShip.y));
			} else {
				mGun.add(new FireGun(mShip.x, mShip.y));
			}

			if (!isAutoFire) mShip.dir = 0;		// 미사일 발사시 우주선 정지
			loop = 0;

			if (MyGameView.isSound)
				MyGameView.sdPool.play(MyGameView.sdFire, 1, 1, 9, 0, 1);
		}
		
		//-------------------------------------
		//  Move All
		//-------------------------------------
		public void MoveAll() {
			loop++;
			// Boss Mode인가?
			if (isBoss) {
				mBoss.Move();
				// Boss Missile
				for (int i = mBsMissile.size() - 1; i >= 0; i--)
					if (mBsMissile.get(i).Move())
						mBsMissile.remove(i);
			}
			// 적군
			for (int i = 5; i >= 0; i--) {
				for (int j = 0; j < 8; j++)
					mEnemy[i][j].Move();		// 
			}
			// 적군 미사일
			for (int i = mMissile.size() - 1; i >= 0; i--) {
				if (mMissile.get(i).Move())
					mMissile.remove(i);
			}
			// 아군 미사일 
			for (int i = mGun.size() - 1; i >= 0; i--) {
				if (mGun.get(i).Move())
					mGun.remove(i);
			}
			// 보너스 
			for (int i = mBonus.size() - 1; i >= 0; i--) {
				if (mBonus.get(i).Move())
					mBonus.remove(i);
			}	
			// 폭파 불꽃 
			for (int i = mExp.size() - 1; i >= 0; i--) {
				if (mExp.get(i).Explode())
					mExp.remove(i);
			}
			// 아군기
			if (!mShip.isDead)
				mShip.Move();
		}

		//-------------------------------------
		//  점수 표시
		//-------------------------------------
		public void DrawScore(Canvas canvas) {
			int x, x1, x2, y = 30;
			x1 = 134;							// HP 위치
			x2 = x1 + mShip.shield * 8 + 4;		// undead 위치
			x = mShip.undeadCnt / 2;

			for (int i = 0; i < shipCnt; i++)
				canvas.drawBitmap(imgMiniShip, i * 20 + 10, y - 15, null);
			
			// HP
			canvas.drawText("HP", 100, y, paint); 
			paint.setColor(0xFF00A0F0);
			for (int i = 0; i < mShip.shield; i++)
				canvas.drawRect(i * 8 + x1, y - 10, i * 8 + x1 + 6 , y - 4, paint);
			
			// undead
			paint.setColor(Color.RED);
			canvas.drawRect(x2, y - 10, x2 + x, y - 4, paint);

			// Score
			paint.setColor(Color.WHITE);
			canvas.drawText("Score " + score, 220, y, paint); 
			canvas.drawText("Stage " + stageNum, 400, y, paint); 
		}
		
		//-------------------------------------
		//  DrawAll
		//-------------------------------------
		public void DrawAll(Canvas canvas) {
			// 배경화면
			canvas.drawBitmap(imgBack, 0, 0, null);	
			// 적군
			for (int i = 5; i >= 0; i--) {
				for (int j = 0; j < 8; j++) {
					if (mEnemy[i][j].isDead) continue;
					canvas.drawBitmap(mEnemy[i][j].imgSprite, mEnemy[i][j].x - sw[i],  
							mEnemy[i][j].y - sh[i], null);
				}	
			}	
			// Boss Mode인가?
			if (isBoss) {
				// Boss Missile
				for (BossMissile tmp : mBsMissile)
					canvas.drawBitmap(tmp.imgMissile, tmp.x - tmp.w, tmp.y - tmp.h, null);
				// Boss
				canvas.drawBitmap(mBoss.imgBoss, mBoss.x - mBoss.w, mBoss.y - mBoss.h, null);
			}
			// 적군 미사일 
			for (Missile tmp : mMissile)
				canvas.drawBitmap(tmp.imgMissile, tmp.x - 1, tmp.y - 1, null);
			// 아군 미사일
			for (FireGun tmp : mGun)
				canvas.drawBitmap(tmp.imgGun, tmp.x - tmp.w, tmp.y - tmp.h, null);
			// 보너스
			for (Bonus tmp : mBonus) 
					canvas.drawBitmap(tmp.imgBonus, tmp.x - tmp.w, tmp.y - tmp.h, null);
			// 아군기
			if (!mShip.isDead)
					canvas.drawBitmap(mShip.imgShip, mShip.x - mShip.w, mShip.y - mShip.h, null);  
			// 폭파 불꽃
			for (Explosion tmp : mExp)
					canvas.drawBitmap(tmp.imgExp, tmp.x - tmp.w, tmp.y - tmp.h, null);
			// Score
			DrawScore(canvas);
		}
		
		//-------------------------------------
		//  스레드 본체
		//-------------------------------------
		public void run() {
			Canvas canvas = null; 				
			while (canRun) {
				canvas = mHolder.lockCanvas();	
				try {
					synchronized (mHolder) {	
						switch (status) {
						case PROCESS :
							if (isAutoFire) FireGunship();
							CheckCollision();			// 충돌 판정
							MoveAll();					// 모든 캐릭터 이동
							AttackSprite();				// 적기 공격
							DrawAll(canvas);			// Canvas에 그리기
							break;
						case STAGE_CLEAR:
							mClear.SetClear(canvas);
							break;
						case ALL_CLEAR:
						case GAMEOVER:
							mGameOver.SetOver(canvas);
							break;
						}
					} // sync
				} finally {						 
					if (canvas != null)			
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
	//  onTouch Event
	//-------------------------------------
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return true;
		synchronized (mHolder) {
			int x = (int) event.getX();
			int y = (int) event.getY();

			// GameOver시 Y/N 판정 
			if (status == GAMEOVER || status == ALL_CLEAR) {
				return mGameOver.TouchEvent(x, y);
			}

			if (!mShip.isDead) { 
				mShip.dir = 0;
				// 아군기 Touch이면 미사일 발사
				if (Math.abs(x - mShip.x) < mShip.w * 2 &&
						Math.abs(y - mShip.y) < mShip.h * 2) {
					mThread.FireGunship();
				} else if (x < mShip.x - mShip.w) {
					mShip.dir = 1;
				} else if (x > mShip.x + mShip.w) {
					mShip.dir = 2;
				}
			} // if
			if (DEBUG)		// 디버그 모드에서 적 상태 파악
				CheckEnemyStatus(x, y);
		} // sync
		return true;
	}

	//-------------------------------------
	//  onKeyDown
	//-------------------------------------
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mShip.isDead) return false;
		synchronized (mHolder) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT :
				mShip.dir = 1;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT :
				mShip.dir = 2;
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				mThread.FireGunship();
				break;
			default:	
				mShip.dir = 0;
			}
		}
		return false;
	}
	
	//-------------------------------------
	//   적의 상태 파악 - DEBUG 모드에서 호출됨
	//-------------------------------------
	private void CheckEnemyStatus(int x, int y) {
		int x1, y1, w;
		
		for (int i = 0; i < 6; i++ ) {
			for (int j = 0; j < 8; j++ ) {
				if (mEnemy[i][j].isDead) continue;
				x1 = mEnemy[i][j].x;
				y1 = mEnemy[i][j].y;
				w = mEnemy[i][j].w;
				if (Math.abs(x - x1) < w &&	Math.abs(y - y1) < w) {
					Log.v("Sprite", "i=" + i + ", j=" + j + "  " + mEnemy[i][j].status);
					return;
				}
			}
		}
	}

} // SurfaceView 
