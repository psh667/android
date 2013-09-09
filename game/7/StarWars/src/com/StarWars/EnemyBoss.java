package com.StarWars;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//--------------------------------
// 적군 Boss
//--------------------------------
public class EnemyBoss {
	final static int CENTER = 0;		// 중앙
	final static int LEFT = 1;			// 왼쪽
	final static int RIGHT = 2;			// 오른쪽
	final static int BOTH = 3;			// 전체

	public int x, y;					// 캐릭터의 좌표
	public int w, h;					// 크기
	public int imgNum;					// 이미지 번호
	public int shield[] = {0, 0, 0};	// 보호막

	public Bitmap imgBoss;				// 현재의 이미지	
	public Bitmap imgSpt[];

	private int sx, sy;					// 캐릭터 이동 속도
	private int dir;					// 좌우 이동 방향
	private int loop;
	private int arShield[] = {20, 30, 40};	// 난이도에 따른 보호막

	//--------------------------------
	// 생성자
	//--------------------------------
	public EnemyBoss() {
		shield = new int [3];
		imgSpt = new Bitmap[4];
		
		for (int i = 0; i < 4; i++) 
			imgSpt[i] = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.boss0 + i);
		
		w = imgSpt[3].getWidth() / 2;
		h = imgSpt[3].getHeight() / 2;
	}
	
	//--------------------------------
	// Boss 초기화
	//--------------------------------
	public void InitBoss() {
		shield[LEFT] = shield[RIGHT] = arShield[MyGameView.difficult];
		shield[CENTER] = shield[LEFT] * 2;

		x = MyGameView.Width / 2;
		y = -60;
		
		sy = 4;
		sx = 4;
		dir = 0;
		loop = 0;
		imgBoss = imgSpt[BOTH];
	}
	
	//--------------------------------
	// Boss 이동
	//--------------------------------
	public void Move() {
		x += sx * dir;
		y += sy;
		if (y > 100) {
			sy = 0;
			if (dir == 0) dir = 1;
		}
		
		if (x < 100 || x > MyGameView.Width - 100)
			dir = -dir;
		
		loop++;
		if (loop % 50 > 0) return;
		MyGameView.mBsMissile.add(new BossMissile(x, y, CENTER));
		if (shield[LEFT] > 0)
			MyGameView.mBsMissile.add(new BossMissile(x - w / 2, y, LEFT));
		if (shield[RIGHT] > 0)
			MyGameView.mBsMissile.add(new BossMissile(x + w / 2, y, RIGHT));
	}
}
