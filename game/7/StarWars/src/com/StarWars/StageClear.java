package com.StarWars;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

//--------------------------------
// 다음 스테이지로 넘어감
//--------------------------------
public class StageClear {
	private int mx, my;
	private int mw;
	private Bitmap imgMsg;
	private int loop;
	
	//--------------------------------
	// 생성자
	//--------------------------------
	public StageClear() {
		imgMsg = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.msg_clear);
		mw = imgMsg.getWidth();
		
		mx = (MyGameView.Width - mw) / 2;
		my = 300;
		loop = 0;
	}
	
	//--------------------------------
	// Set Clear
	//--------------------------------
	public void SetClear(Canvas canvas) {
		int x, y, w, h; 
		boolean isFinish;

		// 배경 이미지
		canvas.drawBitmap(MyGameView.imgBack, 0, 0, null);
		// 점수 표시
		MyGameView.mThread.DrawScore(canvas);
		
		MyGameView.mShip.dir = 3;
		isFinish = MyGameView.mShip.Move();	

		x = MyGameView.mShip.x;
		y = MyGameView.mShip.y;
		w = MyGameView.mShip.w;
		h = MyGameView.mShip.h;
		
		// 아군기 표시
		canvas.drawBitmap(MyGameView.mShip.imgShip, x - w, y - h, null);  
		
		loop++;
		// Message 표시
		if (loop % 12 / 6 == 0)		// 메시지 깜박거림 
			canvas.drawBitmap(imgMsg, mx, my, null);
		
		if (isFinish) {				// 아군기가 화면위에 도착
			canvas.drawBitmap(imgMsg, mx, my, null);
			MyGameView.mShip.dir = 0;
			loop = 0;
			setNextStage();
		}
	}
	
	//--------------------------------
	// 다음 스테이지 준비
	//--------------------------------
	public void setNextStage() {
		// 화면 위의 모든 객체 제거
		MyGameView.mMissile.clear();	// 적군 미사일
		MyGameView.mGun.clear();		// 아군 마시일
		MyGameView.mBonus.clear();		// 보너스
		MyGameView.mExp.clear();		// 폭파 불꽃
		
		MyGameView.stageNum++;
		if (MyGameView.stageNum > MyGameView.MAX_STAGE) {
			MyGameView.status = MyGameView.ALL_CLEAR;			// 마지막 Clear
			MyGameView.stageNum = MyGameView.MAX_STAGE;
		}	
		else {
			MyGameView.MakeStage();								// 새로운 스테이지 준비
			MyGameView.status = MyGameView.PROCESS;
		}
	}
}
