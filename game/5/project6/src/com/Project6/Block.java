package com.Project6;

import android.content.*;
import android.graphics.*;

//-----------------------------
// Block 
//-----------------------------
class Block {
	public int x1, y1, x2, y2, score;	// 블록 좌표, 점수
	public Bitmap imgBlk;				// 이미지
	
	//-----------------------------
	// 생성자 (Constructor) 
	//-----------------------------
	public Block(Context context, float x, float y, float num) {
		x1 = (int) (MyGameView.M_left + x * MyGameView.B_width);
		y1 = (int) (MyGameView.M_top + y * MyGameView.B_height);

		x2 = x1 + MyGameView.B_width - 3;
		y2 = y1 + MyGameView.B_height - 3;
		score = (int) (num + 1) * 50;
		imgBlk = BitmapFactory.decodeResource(context.getResources(), R.drawable.k0 + (int) num);
		imgBlk = Bitmap.createScaledBitmap(imgBlk, MyGameView.B_width, MyGameView.B_height, true);
	}
}

//-----------------------------------------------------

//-----------------------------
// Ball 
//-----------------------------
class Ball {
	public int x, y, bw, bh;		// 공의 위치, 크기
	public int sx, sy;				// 공의 속도
	public boolean isMove = false;	// 이동중인가
	public Bitmap imgBall;			// 이미지

	private int width, height;
	
	//-----------------------------
	// 생성자 (Constructor) 
	//-----------------------------
	public Ball(Context context, int _x, int _y, int _width, int _height) {
		x = _x;
		y = _y;
		width = _width;
		height = _height;
		sx = 3;			// 초기 속도
		sy = -4;
		imgBall = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
		bw = imgBall.getWidth() / 2;
		bh = bw;
	}
	
	//-----------------------------
	// Move 
	//-----------------------------
	public boolean Move() {
		if (isMove == false) return true;
		x += sx;
		if (x < bw || x > width - bw) {		// 좌우 벽
			sx = -sx;
			x += sx;
		}
		
		y += sy;
		if (y >= height + bh) return false;	// 바닥	
		if (y < bh) {						// 천정
			sy = -sy;				
			y += sy;
		}
		return true;
	}
}

//-----------------------------------------------------

//-----------------------------
// 패들 
//-----------------------------
class Paddle {
	public int x, y, pw, ph;	// 좌표
	public Bitmap imgPdl;		// 이미지
	public int sx; 

	private int width;
	
	//-----------------------------
	// 생성자 (Constructor) 
	//-----------------------------
	public Paddle(Context context, int _x, int _y, int _width) {
		x = _x;			// 패들 현재 좌표
		y = _y;
		width = _width;
		pw = MyGameView.B_width * 4 / 6;	// 패들 폭 - 블록의 4/3크기
		ph = MyGameView.B_width / 6;		// 패들 높이 - 블록 높이 1/3

		imgPdl = BitmapFactory.decodeResource(context.getResources(), R.drawable.paddle);
		imgPdl = Bitmap.createScaledBitmap(imgPdl, pw * 2, ph * 2, true);
	}
	
	//-----------------------------
	//  Move 
	//-----------------------------
	public void Move() {
		x += sx;
		if (x < pw || x > width - pw ) sx = 0;
	}
}

