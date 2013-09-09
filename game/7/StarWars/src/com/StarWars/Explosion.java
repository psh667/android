package com.StarWars;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//---------------------------------
// 폭파
//---------------------------------
public class Explosion {
	final static int BIG = 0; 
	final static int SMALL = 1; 
	final static int MYSHIP = 2; 
	final static int BOSS = 3; 
	
	public int x, y;			// 좌표
	public int w, h;			// 폭과 높이
	public boolean isDead;		// 사망
	public Bitmap imgExp;		// 이미지
	
	private Bitmap imgTemp[] = new Bitmap[6];
	private int kind;			// 폭파 종류 (1:작은 폭발, 0:큰 폭발, 2:아군기 폭파, 3:보스)
	private int expCnt = -1;	// 폭파 진행 카운더
	private int delay = 15;		// 아군기 폭파 처리 후 지연 시간
	
	//---------------------------------
	// 생성자
	//---------------------------------
	public Explosion(int x, int y, int kind) {
		this.x = x;
		this.y = y;
		this.kind = kind;
		
		int n = kind;
		if (n == BOSS) n = BIG;

		for (int i = 0; i < 6; i++) {
			imgTemp[i] = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.exp00 + n * 6 + i);
		}

		w = imgTemp[0].getWidth() / 2;
		h = imgTemp[0].getHeight() / 2;
	}
	
	//---------------------------------
	// 폭파
	//---------------------------------
	public boolean Explode() {
		expCnt++;
		int num = expCnt; 
		
		// 아군기 & Boss는 천천히 폭파되도록 처리
		if (kind == MYSHIP || kind == BOSS ) {
			num = expCnt / 3; 
			if (num > 5) num = 5;
		}
		
		// 사운드, 진동 처리
		if (expCnt == 1) {
			switch (kind) {
			case SMALL:
				if (MyGameView.isSound)
					MyGameView.sdPool.play(MyGameView.sdExp0, 1, 1, 9, 0, 1);
				if (MyGameView.isVibe)
					MyGameView.vibe.vibrate(50);
				break;
			case BIG:
				if (MyGameView.isSound)
					MyGameView.sdPool.play(MyGameView.sdExp1, 1, 1, 9, 0, 1);
				if (MyGameView.isVibe)
					MyGameView.vibe.vibrate(100);
				break;
			case MYSHIP:	
				if (MyGameView.isSound)
					MyGameView.sdPool.play(MyGameView.sdExp2, 1, 1, 9, 0, 1);
				if (MyGameView.isVibe)
					MyGameView.vibe.vibrate(100);
				break;
			case BOSS:	
				if (MyGameView.isSound)
					MyGameView.sdPool.play(MyGameView.sdExp3, 1, 1, 9, 0, 1);
				if (MyGameView.isVibe)
					MyGameView.vibe.vibrate(100);
			}
		}
		
		imgExp = imgTemp[num];			// 불꽃 표시
		if (num < 5) return false;		// 폭파 진행 중
		
		switch (kind) {
		case SMALL:
			return true;
		case MYSHIP:	
			return ResetGunShip();
		default :
			return CheckClear();
		}
		
	}

	//----------------------------
	//  스테이지 Clear 판정
	//----------------------------
	public static boolean CheckClear() {
		// 스테이지 진행중
		if (MyGameView.mMap.enemyCnt > 0 || MyGameView.mExp.size() > 1)
			return true;
		
		// 일반 스테이지 끝남
		if (MyGameView.stageNum % MyGameView.BOSS_COUNT > 0) { 
			MyGameView.status = MyGameView.STAGE_CLEAR;	
			return true;
		}
		
		// Boss 스테이지 진행 중
		if (MyGameView.mBoss.shield[EnemyBoss.CENTER] > 0) 
			return true;
		
		// Boss 스테이지 끝남
		if (MyGameView.mBoss.shield[EnemyBoss.CENTER] < 0 ) {
			MyGameView.mBoss.y = -60;						// Boss 화면에서 제거
			MyGameView.mBoss.shield[EnemyBoss.CENTER] = 0;	// 0으로 초기화 - 중요함
			MyGameView.isBoss = false;
			MyGameView.status = MyGameView.STAGE_CLEAR;
			return true;
		}
			
		// Boss Stage 추가 - Boss CENTER가 0일 때
		MyGameView.MakeBossStage();
		return true;
	}

	//----------------------------
	//  아군기 초기화
	//----------------------------
	public boolean ResetGunShip() {
		if (--delay > 0) return false;
		
		if (MyGameView.shipCnt >= 0) {
			MyGameView.mShip.ResetShip();

			// 새 우주선은 보너스를 모두 무효로 처리함 
			MyGameView.isPower = MyGameView.isDouble = false;
			MyGameView.gunDelay = 15;
		} else { 
			MyGameView.mShip.y = -40;	// GameOver	
			MyGameView.status = MyGameView.GAMEOVER; 
		}
		return true;
	}
	
}
