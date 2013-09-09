package com.StarWars;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//--------------------------------
// 아군 미사일
//--------------------------------
public class FireGun {
	public int x, y;		// 좌표
	public int w, h;		// 폭과 높이
	public boolean isDead;	// 사망
	public Bitmap imgGun;	// 미사일 이미지

	public int kind;		// 미사일 종류 (0:보통, 1:강화)
	private float sy;		// 이동 속도
		
	//--------------------------------
	// 생성자
	//--------------------------------
	public FireGun(int x, int y) {
		this.x = x;
		this.y = y;
		
		kind = (MyGameView.isPower) ? 1 : 0;	// 미사일 종류
		imgGun = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.missile1 + kind);
		
		w = imgGun.getWidth() / 2;
		h = imgGun.getHeight() / 2;
		sy = -10;
		Move();
	}

	//--------------------------------
	// Move
	//--------------------------------
	public boolean Move() {
		y += sy;
		return (y < 0);
	}

}
