package com.StartGame;

import java.io.*;
import java.util.*;

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.net.*;
import android.provider.MediaStore.*;
import android.util.*;
import android.view.*;
import android.view.SurfaceHolder.Callback;

public class MyGameView extends SurfaceView implements Callback {

	GameThread 	  mThread;				// GameThread
	SurfaceHolder mHolder;				// SurfaceHolder 
	static Context 	  mContext;			// Context 
	
	static int backGround;				// 백그라운드 종류
	int imageType;						// Slice 이미지 종류
	int imageId;						// Slice 이미지 Id
	int storageType;					// 이미지 저장 매체

	static boolean isThumb = false;		// 썸네일 표시 여부 (옵션메뉴)
	static boolean isSave;				// 파일 Save - Thread 통지용
	static boolean isLoad;				// 파일 Load - Thread 통지용
	
	static int Width, Height;			// View의 크기
	static int mgnLeft, mgnTop;			// 왼쪽, 위 여백
	static int sMax;					// Slice 개수 
	static int xCnt, yCnt;				// Slice 가로, 세로 개수
	static int pWidth, pHeight;			// 사진 사이즈
	static int sWidth, sHeight;			// Slice 사이즈
	int stageNum;						// 스테이지 번호
	int sliceNum[] = new int[36];		// Slice 번호 - 최대 6*6
	
	Slice mSlice[] = new Slice[36]; 	// Slice Class
	Score mScore;						// 점수 처리용
	Rect mRect = new Rect();			// 사진 전체 영역 - Touch를 위함
	
	static Bitmap imgOrg;				// 원본 이미지
	Bitmap imgBack[] = new Bitmap[2];	// 배경 이미지
	Bitmap imgFrame[] = new Bitmap[2];	// 사진 프레임
	Bitmap imgThumb;					// 썸네일
	
	static long startTime;				// 스테이지 시작 시각
	int  moveCnt;						// Slice 이동 횟수
	int msgNum;							// 메시지 번호
	Bitmap imgMsg[] = new Bitmap[6];	// 메시지

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

		// Options Menu의 전역 변수 읽기
		xCnt = ((GlobalVars) context.getApplicationContext()).getXCount();
		yCnt = ((GlobalVars) context.getApplicationContext()).getYCount();
		backGround = ((GlobalVars) context.getApplicationContext()).getBackground();
		isLoad = ((GlobalVars) context.getApplicationContext()).getLoad();

		// ImageType 읽기
		imageType = ((GlobalVars) mContext.getApplicationContext()).getImageType();
		imageId = ((GlobalVars) mContext.getApplicationContext()).getImageId();
		storageType = ((GlobalVars) mContext.getApplicationContext()).getStorageType();
		
		if (xCnt < 3 || xCnt > 6) xCnt = 3;		// 유효범위 조사
		if (yCnt < 3 || yCnt > 6) yCnt = 3;
		if (backGround < 1 || backGround > 2) backGround = 1;
		
		stageNum = 0;			// 스테이지 번호
		
		InitGame();				// 게임 초기화
		MakeStage();			// 스테이지 만들기

		setFocusable(true);
		Log.v("surface Created", "---------------------------");
	}

	//-------------------------------------
	//  변수 초기화 - 생성자에서 호출
	//-------------------------------------
	private void InitGame() {
		Display display = ((WindowManager) mContext.getSystemService (Context.WINDOW_SERVICE)).getDefaultDisplay();
		Width = display.getWidth();			
		Height = display.getHeight() - 50;  
		
		mgnLeft = 60;						// 왼쪽, 위 여백
		mgnTop = (int)(Height / 7.5);		

		pWidth = Width - mgnLeft * 2;		// 사진 가로
		pHeight = pWidth * 7 / 5;			// 사진 세로 (5:7)

		// 사진 크기를 Rect로  - Touch를 위함
		mRect.set(mgnLeft, mgnTop, mgnLeft + pWidth, mgnTop + pHeight);
		mScore = new Score();
		
		// 메시지 이미지 읽기
		for (int i = 0; i < 5; i++)	{	
			// 메시지 번호는 1부터 시작
			imgMsg[i + 1] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.msg1 + i);
		}	
	}
	
	//-------------------------------------
	//  스테이지 만들기 - 생성자에서 호출
	//-------------------------------------
	private void MakeStage() {
		sMax = xCnt * yCnt;				// Slice 갯수
		sWidth = pWidth / xCnt;			// Slice 크기
		sHeight = pHeight / yCnt;
		
		// 배열에 Slice 일련번호 번호 넣기
		for (int i = 0; i < sMax; i++) { 
			sliceNum[i] = i;
		}

		LoadImages();	// 원본 사진  Trimming
		Shuffling();	// Slice 번호 섞고 무결성 조사
		
		// Slice()를 배열에 넣기
		for (int i = 0; i < sMax; i++) {
			mSlice[i] = null;	// 다음판을 위해 먼저 clear
			mSlice[i] = new Slice(sliceNum[i], i);
		}
		
		startTime = System.currentTimeMillis();
		moveCnt = 0;
		mScore.MakeCount(moveCnt);
		mScore.MakeStageNum(stageNum);
		mScore.MakeTime(startTime);
	}
	
	//-------------------------------------
    //  LoadImage & Rescale - MakeStage에서 호출
    //-------------------------------------
	public void LoadImages() {
		Resources res = mContext.getResources();
		int num = stageNum % 4;
		
		// 스테이지 번호에 맞는 배경 이미지 읽고 View의 크기로 Rescale
		imgBack[0] = BitmapFactory.decodeResource(res, R.drawable.snow0 + num);
		imgBack[1] = BitmapFactory.decodeResource(res, R.drawable.sky0 + num);
		imgBack[0] = Bitmap.createScaledBitmap(imgBack[0], Width, Height, true);	
		imgBack[1] = Bitmap.createScaledBitmap(imgBack[1], Width, Height, true);	

		// 사진 프레임
		imgFrame[0] = BitmapFactory.decodeResource(res, R.drawable.frame1);	
		imgFrame[1] = BitmapFactory.decodeResource(res, R.drawable.frame2);	
		imgFrame[0] = Bitmap.createScaledBitmap(imgFrame[0], pWidth + 30, pHeight + 30, true);	
		imgFrame[1] = Bitmap.createScaledBitmap(imgFrame[1], pWidth + 30, pHeight + 30, true);	
		
		// 원본 이미지 읽기
		Bitmap imgtmp = BitmapFactory.decodeResource(res, R.drawable.pic_0 + num);
		
		// 사용자 지정 이미지 읽기
		if (imageType == 2 && imageId > 0) {
			try {
    			Uri myUri;
    			if (storageType == 1)
    				myUri = ContentUris.withAppendedId(Images.Media.INTERNAL_CONTENT_URI, imageId); 
    			else
    				myUri = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, imageId);
				imgtmp = Images.Media.getBitmap(mContext.getContentResolver(), myUri); 
			} catch (FileNotFoundException e) { 
				e.printStackTrace(); 
			} catch (IOException e) { 
				e.printStackTrace(); 
			}
		} // if
		
		int w = imgtmp.getWidth();		// 이미지 크기 
		int h = imgtmp.getHeight();

		// 사진의 비율이 5:7이 되도록 남는 부분 자르기
		if (w * 1.4 < h)				// 세로로 길쭉이면 아래쪽 절단
			imgtmp = Bitmap.createBitmap(imgtmp, 0, 0, w, (int) (w * 1.4));   
		else if (w * 1.4 > h) {			// 가로로 길쭉이면 사진 좌우 절단
			int p = (int) (w - h / 1.4) / 2;	// 좌우 남는 부분의 길이
			imgtmp = Bitmap.createBitmap(imgtmp, p, 0, (int) (h / 1.4), h);
		}
		imgOrg = Bitmap.createScaledBitmap(imgtmp, pWidth, pHeight, true);	
		imgThumb = Bitmap.createScaledBitmap(imgOrg, (int)(pWidth / 4), (int)(pHeight / 4), true);
	}

	
	//-------------------------------------
	//  Slice 섞기 - MakeStage()에서 호출
	//-------------------------------------
	public void Shuffling() {
		int r, t;
		Random rnd = new Random();
		for (int i = 0; i < sMax; i++) {
			r = rnd.nextInt(sMax);		// 0 ~ x*y-1
			t = sliceNum[i];			// 현재 위치와 난수위치 교환
			sliceNum[i] = sliceNum[r];
			sliceNum[r] = t;
		} // for

		CheckShuffle();	// 무결성 조사
	} // Shuffle

	//-------------------------------------
	//  무결성 조사 - Shuffling()에서 호출
	//-------------------------------------
	public void CheckShuffle() {
		int k1, k2, cnt;
		// (6, 5)처럼 앞의 숫자가 큰 치환의 조합이 몇 개인지 조사
		do {
			k1 = k2 = cnt = 0;
			for (int i = 0; i < sMax - 1; i++) {
				// 공백은 조사 대상에서 제외
				if (sliceNum[i] == sMax - 1) continue;
				for (int j = i + 1; j < sMax; j++) {
					if (sliceNum[j] == sMax - 1) continue;
					if (sliceNum[i] > sliceNum[j]) {
						cnt++;		// 치환의 갯수
						k1 = i;		// 치환 번호 임시저장
						k2 = j;
					}
				} // for j
			} // for i
			if (cnt % 2 == 0) break;	// 짝수 치환이면 정상
		
			// 마지막 치환을 바꿔서 다시 조사
			int t = sliceNum[k1];
			sliceNum[k1] = sliceNum[k2];
			sliceNum[k2] = t;
		} while (true);	
	} // Check

	//-------------------------------------
	//  Slice 이동 - Touch에서 호출
	//-------------------------------------
	public void MoveSlice(int x, int y) {
		synchronized (mHolder) {
			x = (x - mgnLeft) / sWidth;	// Slice의 평면 좌표
			y = (y - mgnTop) / sHeight;
		
			int p  = y * xCnt + x;		// Slice의 배열 좌표 
			int pl = p - 1;				// Slice의 왼쪽 
			int pr = p + 1;				// Slice의 오른쪽 
			int pu = p - xCnt;			// Slice의 위 
			int pd = p + xCnt;			// Slice의 아래
			int last = sMax - 1;		// 마지막 Slice 번호		

			// Slice의 상하좌우에 공백이 있는지 조사 - 평면좌표로 판단
			if (x - 1 >= 0 && sliceNum[pl] == last) {	  
				CheckSlice(p, pl);	// 왼쪽
			} 
			else if (x + 1 < xCnt && sliceNum[pr] == last) {
				CheckSlice(p, pr);	// 오른쪽
			}	
			else if (y - 1 >= 0 && sliceNum[pu] == last) {
				CheckSlice(p, pu);	// 위
			}
			else if (y + 1 < yCnt && sliceNum[pd] == last) {
				CheckSlice(p, pd);	// 아래
			}
		} // sync
	}
	
	//-------------------------------------
	//  Slice정리 - StartMove(), Thread에서 호출
	//-------------------------------------
	public void CheckSlice(int p1, int p2) {
		synchronized (mHolder) {
			moveCnt++;
			mScore.MakeCount(moveCnt);
			
			Bitmap tmp;						// 임시로 비트맵을 1개 만든다
			// Class의 비트맵만 교환
			tmp = mSlice[p1].imgPic;		
			mSlice[p1].imgPic = mSlice[p2].imgPic;
			mSlice[p2].imgPic = tmp;

			// 배열 순서 교환
			int t = sliceNum[p1];
			sliceNum[p1] = sliceNum[p2]; 
			sliceNum[p2] = t;
			
			// 스테이지 Clear 여부 조사
			int n;
			for (n = 0; n < sMax; n++) {
				if (sliceNum[n] != n) break;
			}
			if (n >= sMax) msgNum = 5;			// 스테이지 Clear 
		} // sync
	}
	
	//-------------------------------------
    //  다음 스테이지 - Thread에서 호출
    //-------------------------------------
	public void MakeNextStage() {
		PauseGame();		// 스레드 일시 정지
		stageNum++;
		if (xCnt == yCnt)	// Slice 갯수 증가
			yCnt++;
		else
			xCnt++;
		if (xCnt > 6) xCnt = 6;
		if (yCnt > 6) yCnt = 6; 

		MakeStage();
		ResumeGame();		// 스레드 재 기동
	}
	
	//-------------------------------------
    //  게임 저장 - Thread에서 호출
    //-------------------------------------
	public void SaveStage() {
		isSave = false;
		PauseGame();	// 게임 일시 정지
		int time = (int)(System.currentTimeMillis() - startTime);	// 경과시간

		StringBuffer buffer = new StringBuffer();
		buffer.append(stageNum).append("|")			// 스테이지 번호
			  .append(xCnt).append("|")				// Slice 조각 수
			  .append(yCnt).append("|")
			  .append(moveCnt).append("|")			// 이동 횟수
			  .append(time).append("|")				// 경과 시간
			  .append(backGround).append("|")		// Background 종류
			  .append(imageType).append("|")		// 이미지 종류(기본/사용자)
			  .append(imageId).append("|")			// 이미지 Id
			  .append(storageType).append("|");		// 저장매체 (1:내장, 2:외장)
		for (int i = 0; i < sMax; i++) {
			buffer.append(sliceNum[i]).append("|");	// 배열 상태
		}	  
		
		try {
			FileOutputStream fileOutput = mContext.openFileOutput("SlidingPuzzle", Context.MODE_PRIVATE);
			fileOutput.write(buffer.toString().getBytes()); 
			fileOutput.close();
			msgNum = 1;
		} catch (IOException e) {
			msgNum = 2;
		}
		ResumeGame();	// 게임 진행
	}

	//-------------------------------------
    //  게임 불러오기 - 생성자/Thread에서 호출
    //-------------------------------------
	public boolean LoadStage() {
		isLoad = false;
		PauseGame();
		boolean result = false;
		try {
			FileInputStream fileInput = mContext.openFileInput("SlidingPuzzle");
			byte[] data = new byte[fileInput.available()];
			if (fileInput.read(data) != -1) {
				fileInput.close();
				
				// 문자열을 '|'으로 분리하여 배열에 넣기
				String[] buffer = (new String(data)).split("\\|");

				stageNum = Integer.parseInt(buffer[0]);
				xCnt = Integer.parseInt(buffer[1]);
				yCnt = Integer.parseInt(buffer[2]);
				moveCnt = Integer.parseInt(buffer[3]);
				int time = Integer.parseInt(buffer[4]);
				backGround = Integer.parseInt(buffer[5]);
				imageType = Integer.parseInt(buffer[6]);
				imageId = Integer.parseInt(buffer[7]);
				storageType = Integer.parseInt(buffer[8]);
					
				LoadImages(); 				// 이미지 읽기				
				sMax = xCnt * yCnt;			
				sWidth = pWidth / xCnt;		// Slice 크기
				sHeight = pHeight / yCnt;
				
				// 배열 읽고 Slice 만들기
				for (int i = 0; i < sMax; i++) {
					sliceNum[i] = Integer.parseInt(buffer[9 + i]);
					mSlice[i] = null;
					mSlice[i] = new Slice(sliceNum[i], i);
				}	
				startTime = System.currentTimeMillis() - time;	// 경과시간 계산용
				mScore.MakeCount(moveCnt);  
				mScore.MakeStageNum(stageNum);  
				mScore.MakeTime(startTime);
				msgNum = 3;
				result = true;
			}
		} catch (IOException e) {
			msgNum = 4;
		}
		ResumeGame();
		return result;
	}
	
	//-------------------------------------
    //  SurfaceView가 생성될 때 실행되는 부분
    //-------------------------------------
	public void surfaceCreated(SurfaceHolder holder) {
		Log.v("surface Started", "---------------------------");
		try {
			mThread.start();
		} catch (Exception e) {
			RestartGame(); 
		}
	}

	//-------------------------------------
    //  SurfaceView가 바뀔 때 실행되는 부분
    //-------------------------------------
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.v("surface Changed", "---------------------------");
	}

	//-------------------------------------
    public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v("surface Destroyed", "---------------------------");
		mThread.StopThread();
		Log.v("Save Stage", "---------------------------");
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
			
		ArrayList<Snow> mSnow1 = new ArrayList<Snow>();		// 배경 
		ArrayList<Snow> mSnow2 = new ArrayList<Snow>(); 	// 전경	
		
		ArrayList<Bubble> mBall = new ArrayList<Bubble>();			// 큰방울
		ArrayList<SmallBall> sBall = new ArrayList<SmallBall>();	// 작은방울
		
		Random rnd = new Random();
		int msgLoop = 0;			// 루프 카운터
		boolean isNext = false;		// 다음 스테이지로 진입
		
		//-------------------------------------
		//  생성자 
		//-------------------------------------
		public GameThread(SurfaceHolder holder, Context context) {
			
		}
		
		//-------------------------------------
		//  비눗방울 만들기  - run()에서 호출
		//-------------------------------------
		public void MakeBubble() {
			if (mBall.size() > 7) return;
			synchronized (mHolder) {
				mBall.add(new Bubble());
			}
		}
		
		//-------------------------------------
		//  비눗방울 Touch - Touch Event에서 호출
		//-------------------------------------
		public void TouchBubble(int x, int y) {
			boolean flag = false;
			for (Bubble tmp :  mBall) {
				if (Math.pow(tmp.x - x, 2) + Math.pow(tmp.y - y, 2) 
						<= Math.pow(tmp.rad, 2)){
					tmp.dead = true;	// 비눗방울 Touch일 경우 
					flag = true;		
				}
			}
		}
	
		//-------------------------------------
		//  작은  비눗방울 만들기 - MoveBubble()에서 호출
		//-------------------------------------
		public void MakeSmallBall(int x, int y) {
			int count = rnd.nextInt(7) + 7;   // 7~13개
			synchronized (mHolder) {
				for (int i = 1; i <= count; i++) {
					int ang = rnd.nextInt(360);		 
					sBall.add(new SmallBall(x, y, ang));
				}
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
		//  눈내리기  - run에서 호출
		//-------------------------------------
		public void MoveSnow() {
			synchronized (mHolder) {
				if (mSnow1.size() < 300)  mSnow1.add(new Snow(2));	// 배경
				if (mSnow2.size() < 100)  mSnow2.add(new Snow(1));	// 전경
			}
			
			int n = rnd.nextInt(300);
			for (Snow tmp : mSnow1) tmp.MoveSnow(n);
			for (Snow tmp : mSnow2) tmp.MoveSnow(n);
		}
		
		//-------------------------------------
		//  안내 문자열 출력 - run()에서 호출
		//-------------------------------------
		public void DrawMessage(Canvas canvas) {
			msgLoop++;
			if (msgLoop % 10 / 5 == 0) {
				int left = (Width - imgMsg[msgNum].getWidth()) / 2;
				canvas.drawBitmap(imgMsg[msgNum], left, Height - 90, null); 
			}	
			
			if (msgLoop >= 25) {				// 메시지 표시는 Blink 3회까지만
				if (msgNum == 5) 				// Stage Clear이면
					synchronized (mHolder) {
						MakeNextStage();		// 다음 Stage를 만든다
					}
				msgLoop = 0;
				msgNum = 0;
			}	
		}
		
		//-------------------------------------
		//  모두 출력 - run()에서 호출 
		//-------------------------------------
		public void DrawAll(Canvas canvas) {
			// 배경 이미지
			canvas.drawBitmap(imgBack[backGround - 1], 0, 0, null);
			
			if (backGround == 1) {	
				for (Snow tmp : mSnow1)
					canvas.drawBitmap(tmp.imgSnow, tmp.x - tmp.rad,  tmp.y - tmp.rad, null);
			}  else { 
				// 큰 비눗방울
				for (Bubble tmp : mBall)
					canvas.drawBitmap(tmp.imgBall, tmp.x - tmp.rad,  tmp.y - tmp.rad, null);
				// 작은비눗방울
				for (SmallBall tmp : sBall)
					canvas.drawBitmap(tmp.imgBall, tmp.x - tmp.rad,  tmp.y - tmp.rad, null);
			}
			
			// 사진 Frame
			canvas.drawBitmap(imgFrame[backGround - 1], mgnLeft - 15, mgnTop - 15, null);	

			// 썸네일
			if (isThumb == true)	
				canvas.drawBitmap(imgThumb, 5, Height - imgThumb.getHeight() - 5, null);
			
			// 이동횟수 & Stage & 진행시간 계산
			mScore.MakeTime(startTime);
			canvas.drawBitmap(mScore.imgScore, 15, 15, null); 
			canvas.drawBitmap(mScore.imgStage, Width / 2 - mScore.imgStage.getWidth() / 2, 15, null);
			canvas.drawBitmap(mScore.imgTime, Width - mScore.imgTime.getWidth() - 15, 15, null);
			
			// Slice 표시
			for (int i = 0; i < sMax; i++)
				canvas.drawBitmap(mSlice[i].imgPic, mSlice[i].x, mSlice[i].y, null);

			if (backGround == 1) {
				for (Snow tmp : mSnow2)
					canvas.drawBitmap(tmp.imgSnow, tmp.x - tmp.rad,  tmp.y - tmp.rad, null);
			}
		}
		
		//-------------------------------------
		//  스레드 본체
		//-------------------------------------
		public void run() {
			Canvas canvas = null; 					// canvas를 만든다
			while (canRun) {
				canvas = mHolder.lockCanvas();		// canvas를 잠그고 버퍼 할당
				try {
					synchronized (mHolder) {
						if (backGround == 1) {
							MoveSnow();					// 눈내리기
						}
						else {
							MakeBubble();				// 풍선 만들기
							MoveBubble();				// 풍선 이동
						}
						DrawAll(canvas);				// 모두 출력
						if (isSave) SaveStage();		// Save
						if (isLoad) LoadStage();		// Load
						if (msgNum > 0) DrawMessage(canvas);	// 메시지 출력	
					}
				} finally {								// 버퍼 작업이 끝나면 
					if (canvas != null)					// 버퍼의 내용을 View에 전송
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
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			int x = (int) event.getX(); 
			int y = (int) event.getY();
			synchronized(mHolder)  {
				if (mRect.contains(x, y)) 
					MoveSlice(x, y);
				else if (backGround == 2)
					mThread.TouchBubble(x, y);
			}
		}
		return true;
	} // Touch

} // SurfaceView 
