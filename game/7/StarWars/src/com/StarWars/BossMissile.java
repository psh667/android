package com.StarWars;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//--------------------------------
//Boss Missile - 보스 미사일
//--------------------------------
public class BossMissile {
	public int x, y;			// 좌표
	public int w, h;			// 폭과 높이
	public boolean isDead;		// 사망
	public Bitmap imgMissile;	// 미사일 이미지

	private int sx, sy;			// 이동 속도, 방향
	
	//--------------------------------
	// 생성자
	//--------------------------------
	public BossMissile(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		
		imgMissile = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.boss_missile);
		w = imgMissile.getWidth() / 2;
		h = imgMissile.getHeight() / 2;
		
		sy = 10;
		sx = 0;
		if (dir == EnemyBoss.LEFT)
			sx = -2;
		if (dir == EnemyBoss.RIGHT)
			sx = 2;
	}

	//--------------------------------
	// Move
	//--------------------------------
	public boolean Move() {
		x += sx;
		y += sy;
		return (x < w || x > MyGameView.Width + w || y > MyGameView.Height + h);
	}
}	
