package com.StarWars;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

//--------------------------------
// Game Over 처리용
//--------------------------------
public class GameOver {
	final int WAIT = 1;					// 버튼 입력 대기 상태
	final int TOUCH = 2;				// Yes, No 선택
	final int BTN_YES = 1;				// 버튼 번호
	final int BTN_NO = 2;
	
	private int btnWhich;				// 누른 버튼 종류
	private int status = WAIT;			// 현재 상태
	
	private int mx1, my1, mx2, my2;		// 메시지를 표시할 위치
	private int mw1, mw2;				// 메시지 폭
	private int x1, y1, x2, w, h;		// Yes, No 버튼 표시할 위치 
	private Bitmap imgOver, imgAgain;	// 게임 오버 메시지
	private Bitmap imgYes, imgNo;
	private Bitmap imgCong;				// 축하 메시지
	private int loop;					// 화면 처리용 루프 카운터
	private Rect rectYes, rectNo;		// 버튼의 좌표
	
	//--------------------------------
	// 생성자
	//--------------------------------
	public GameOver() {
		// 버튼 이미지
		imgYes = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.btn_yes);
		imgNo = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.btn_no);

		// 메시지 이미지
		imgOver = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.msg_over);
		imgCong = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.msg_all);
		imgAgain = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.msg_again);

		my1 = 260;		// 메시지 표시 위치

		// Try again
		mw2 = imgAgain.getWidth();
		mx2 = (MyGameView.Width - mw2) / 2;
		my2 = 550;

		// 버튼
		y1 = 630;		
		w = imgYes.getWidth();
		h = imgYes.getHeight();
		
		x1 = 100;
		x2 = MyGameView.Width - 100 - w;
		
		// 버튼의 좌표를 Rect로 변환 - Touch를 위함
		rectYes = new Rect(x1, y1, x1 + w, y1 + h);
		rectNo = new Rect(x2, y1, x2 + w, y1 + h);
		loop = 0;
	}
	
	//--------------------------------
	// GameOver & Clear All
	//--------------------------------
	public void SetOver(Canvas canvas) {
		if (MyGameView.status == MyGameView.GAMEOVER)
			mw1 = imgOver.getWidth();
		else
			mw1 = imgCong.getWidth();
		mx1 = (MyGameView.Width - mw1) / 2;
		
		switch (status) {
		case WAIT:
			DisplayAll(canvas);
			break;
		case TOUCH:
			CheckButton();
		}
	}
	
	//--------------------------------
	// Display All
	//--------------------------------
	public void DisplayAll(Canvas canvas) {
		// 배경 이미지
		canvas.drawBitmap(MyGameView.imgBack, 0, 0, null);
		// 점수 표시
		MyGameView.mThread.MoveAll();
		MyGameView.mThread.AttackSprite();
		MyGameView.mThread.DrawAll(canvas);
		
		loop++;
		// Message
		if (loop % 12 / 6 == 0)	{			// 메시지 깜박거림
			if (MyGameView.status == MyGameView.GAMEOVER)
				canvas.drawBitmap(imgOver, mx1, my1, null);
			else
				canvas.drawBitmap(imgCong, mx1, my1, null);
		}	
		
		// 버튼 표시
		canvas.drawBitmap(imgAgain, mx2, my2, null);
		canvas.drawBitmap(imgYes, x1, y1, null);
		canvas.drawBitmap(imgNo, x2, y1, null);
	}
	
	//--------------------------------
	// 버튼에 따른 처리
	//--------------------------------
	private void CheckButton() {
		if (btnWhich == BTN_NO) {	
			MyGameView.GameOver();			// MyGameView의 메소드 호출
			return;
		}
		
		// 게임(스테이지) 다시 시작
		status = WAIT;						// 버튼과 status를 원래 상태로 되돌림
		btnWhich = 0;
		
		// View에 표시된 모든 객체를 제거한다
		MyGameView.mMissile.clear();		// 적군 미사일
		MyGameView.mGun.clear();			// 아군 마사일
		MyGameView.mBonus.clear();			// 보너스
		MyGameView.mExp.clear();			// 폭파 불꽃
		MyGameView.mBoss.InitBoss();		// 보스 초기화
		
		// 첫판부터 시작
		//MyGameView.stageNum = 1;	
		//MyGameView.MakeStage();				// 스테이지 시작
		//MyGameView.score = 0; 
		
		// 현재판부터 시작
		if (MyGameView.stageNum > MyGameView.MAX_STAGE)
			MyGameView.stageNum = MyGameView.MAX_STAGE;

		MyGameView.shipCnt = 3; 
		MyGameView.MakeStage();				// 기본 스테이지 시작
		MyGameView.mShip.ResetShip();			// 우주선 초기화
		MyGameView.status = MyGameView.PROCESS; 
	}

	//-------------------------------------
	//  TouchEvent
	//-------------------------------------
	public boolean TouchEvent(int x, int y) {
		if (rectYes.contains(x, y))	btnWhich = BTN_YES;
		if (rectNo.contains(x, y)) btnWhich = BTN_NO;
		if (btnWhich != 0) status = TOUCH;
		return true;
	}
}
