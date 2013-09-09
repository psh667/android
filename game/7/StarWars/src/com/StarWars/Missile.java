package com.StarWars;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//--------------------------------
// 적군 미사일
//--------------------------------
public class Missile {
	public int x, y, dir;		// 좌표, 발사 방향
	public boolean isDead;		// 사망
	public Bitmap imgMissile;	// 미사일 이미지
	
	private float sx, sy;		// 미사일 이동 속도
	
	//--------------------------------
	// 생성자
	//--------------------------------
	public Missile(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		imgMissile = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.missile0);
		
		sx = MyGameView.mMap.sx[dir];	// 방향에 따른 이동 속도 계산
		sy = MyGameView.mMap.sy[dir];
		Move();
	}

	//--------------------------------
	// Move
	//--------------------------------
	public boolean Move() {
		x += (int) (sx * 10);		// 미사일 이동
		y += (int) (sy * 10);
		
		return (x < 0 || x > MyGameView.Width || y > MyGameView.Height);
	}
}

