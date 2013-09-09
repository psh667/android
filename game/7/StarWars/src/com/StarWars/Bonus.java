package com.StarWars;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;

//--------------------------------
// Bonus - 적기가 파괴되면 나온다
//--------------------------------
public class Bonus {
	public int x, y;			// 좌표
	public int w, h;			// 폭과 높이
	public int kind;			// 보너스 종류
	public boolean isDead;		// 사망
	public Bitmap imgBonus;		// 이미지

	private int sy;				// 이동 속도
	private int imgNum;			// 이미지 번호
	public Bitmap imgTemp[] = new Bitmap[16];
		
	//--------------------------------
	// 생성자
	//--------------------------------
	public Bonus(int x, int y, int kind) {
		this.x = x;
		this.y = y;
		this.kind = kind;
			
		imgTemp[0] = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.bonus0 + (kind - 1)); 
		int sw = imgTemp[0].getWidth();
		int sh = imgTemp[0].getHeight();
		w = sw / 2;
		h = sh / 2;
		
		// 16 방향으로 회전한 이미지 만들기
		Canvas canvas = new Canvas();
		for (int i = 1; i < 16; i++) {
			imgTemp[i] = Bitmap.createBitmap(sw, sh, Config.ARGB_8888);
			canvas.setBitmap(imgTemp[i]);
			canvas.rotate(22.5f, w, h);
			canvas.drawBitmap(imgTemp[0], 0, 0, null);
		}
			
		sy = 2;			// 속도
		imgNum = 0;		// 최초 이미지 번호
		Move();
		
	}
		
	//--------------------------------
	// Move
	//--------------------------------
	public boolean Move() {
		imgBonus = imgTemp[imgNum];		// 이미지 번호
		imgNum++;
		if (imgNum > 15) imgNum = 0;
			
		y += sy;
		return (y > MyGameView.Height + h);
	}
}

