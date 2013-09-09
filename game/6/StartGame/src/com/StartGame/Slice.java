package com.StartGame;

import java.util.*;

import android.graphics.*;
import android.graphics.Bitmap.Config;

public class Slice {
	public int x, y;					// 현재 위치
	public Bitmap imgPic;				// Slice 이미지
	
	private int w, h;					// slice 크기
	
	//-------------------------------------
	//  생성자 - 사진번호, 배열 첨자
	//-------------------------------------
	public Slice(int sliceNum, int pos) {
		w = MyGameView.sWidth;		// 조각 크기
		h = MyGameView.sHeight;
		
		// 배열 번호를 View의 좌표로 전환
		x = pos % MyGameView.xCnt * w + MyGameView.mgnLeft;  
		y = pos / MyGameView.xCnt * h + MyGameView.mgnTop;
		
		// Slice 번호를 사진 좌표로 전환
		int px = sliceNum % MyGameView.xCnt * w; 	
		int py = sliceNum / MyGameView.xCnt * h; 
		
		// 사진 자르기
		imgPic = Bitmap.createBitmap(MyGameView.imgOrg, px, py, w, h);

		// 엠보싱용 Paint
		Paint paint = new Paint();
		EmbossMaskFilter emboss = new EmbossMaskFilter(new float[] {1, 1, 1}, 0.5f, 1, 1);
		paint.setMaskFilter(emboss);
		
		// 맨 마지막 Slice는 반투명한 연노랑색으로 채운다
		Canvas canvas = new Canvas();
		canvas.setBitmap(imgPic);
		if (sliceNum == MyGameView.sMax - 1) {	// 마지막 Slice
			paint.setColor(Color.YELLOW);
			paint.setAlpha(160);
			canvas.drawRect(0, 0, w, h, paint);
		} else	{
			canvas.drawBitmap(imgPic, 0, 0, paint);
		}	
	}
}

//------------------------------------------------------------

//-------------------------------------
//이동 & 시간 & Stage 만들기 
//-------------------------------------
class Score {
	public Bitmap imgScore;				// 이동 횟수
	public Bitmap imgTime;				// 경과 시간
	public Bitmap imgStage;				// Stage
	
	private Bitmap fonts[] = new Bitmap[15];
	private Bitmap imgStg;				// 'STAGE' 문자
	private int fw, fh, sw;				// 글자 폭 & 높이
	private int icoNum = 11;
	private long lastTime;
	
	//-------------------------------------
	//  생성자
	//-------------------------------------
	public Score() {
		for (int i = 0; i < 15; i++)
			fonts[i] = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.f00 + i);
		imgStg = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.f_stage);
		fw = fonts[0].getWidth();
		fh = fonts[0].getHeight();
		sw = imgStg.getWidth(); 
	}

	//-------------------------------------
	//  Count 이미지
	//-------------------------------------
	public void MakeCount(int moveNum) {
		String s = "" + moveNum;
		int L = s.length();				// 문자열의 길이

		// 빈 비트맵을 만든다
		imgScore = Bitmap.createBitmap(fw * (L + 2), fh, Config.ARGB_8888);

		// 비트맵에 이미지를 그려넣을 Canvas 생성
		Canvas canvas = new Canvas();
		canvas.setBitmap(imgScore);			// Canvas가 비트맵에 출력하도록 설정 

		canvas.drawBitmap(fonts[icoNum], 0, 0, null);		// 아이콘
		for (int i = 0; i < L; i++) { 				// Score
			int n = (int) s.charAt(i) - 48;
			canvas.drawBitmap(fonts[n], fw * (i + 1) + 10, 0, null); 
		}
		icoNum++;
		if (icoNum > 14) icoNum = 11;				// 아이콘 애니메이션
	}
	
	//-------------------------------------
	//  시간 이미지
	//-------------------------------------
	public void MakeTime(long startTime) {
		long curTime = System.currentTimeMillis();
		if (startTime > curTime)
			MyGameView.startTime = startTime = curTime;
			
		if (curTime - lastTime < 250) return;
		lastTime = curTime;
		int time = (int) (curTime - startTime) / 1000;
		int sec = time % 60;
		int min = time / 60 % 60;
		int hour = time / 3600;

		String s = hour + ":" + min + ":" + sec;
		int L = s.length();					// 문자열의 길이

		// 빈 비트맵을 만든다
		imgTime = Bitmap.createBitmap(fw * L, fh, Config.ARGB_8888);

		// 비트맵에 이미지를 그려넣을 Canvas 생성
		Canvas canvas = new Canvas();
		canvas.setBitmap(imgTime);    
		for (int i = 0; i < L; i++) { 
			int n = (int) s.charAt(i) - 48;
			canvas.drawBitmap(fonts[n], fw * i, 0, null); 
		}
	}

	//-------------------------------------
	//  Stage 이미지
	//-------------------------------------
	public void MakeStageNum(int stageNum) {
		String s = "" + (stageNum + 1);
		int L = s.length();				// 문자열의 길이

		// 빈 비트맵을 만든다
		imgStage = Bitmap.createBitmap(sw + fw * L, fonts[0].getHeight(), Config.ARGB_8888);

		// 비트맵에 이미지를 그려넣을 Canvas 생성
		Canvas canvas = new Canvas();
		canvas.setBitmap(imgStage);

		canvas.drawBitmap(imgStg, 0, 0, null);		// 'STAGE'
		for (int i = 0; i < L; i++) { 				// stageNum
			int n = (int) s.charAt(i) - 48;
			canvas.drawBitmap(fonts[n], sw + fw * i, 0, null); 
		}
	}
}	

//--------------------------------------------------------------------------------

//-------------------------------------
// Snow
//-------------------------------------
class Snow {
	public int x, y, rad;			// 좌표
	public  Bitmap imgSnow;			// 비트맵 이미지
	
	private int width, height;
	private int sx, sy; 
	private int range, dx, speed;
	private Random rnd;
	
	//-------------------------------------
	//  생성자
	//-------------------------------------
	public Snow(int kind) {
		width = MyGameView.Width;
		height = MyGameView.Height;
		
		rnd = new Random();
		rad = rnd.nextInt(6) + 2; 				// 2~7 반지름
		x = rnd.nextInt(width);					// View 전체
		y = rnd.nextInt(height);
		sy = rnd.nextInt(4) + 2; 				// 2~5 속도
		sx = rnd.nextInt(3) + 1;				// 1~3
		range = rnd.nextInt(31) + 20;			// 20~50 좌우 이동 
		
		int k;									// 0~3 눈  종류
		if (kind == 1)
			k = rnd.nextInt(2);					// 전경
		else 
			k = rnd.nextInt(4);					// 배경
		imgSnow = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), 
						R.drawable.s0 + k);
		imgSnow = Bitmap.createScaledBitmap(imgSnow, rad * 2 , rad * 2, true);
	}

	//-------------------------------------
	//  MoveSnow
	//-------------------------------------
	public void MoveSnow(int dir) {
		if (dir < 5) dir = 1;
		else if (dir < 10) dir = 2; 
		else if (dir > 200) dir = 0;
		
		switch (dir) {
		case 0 :				// 바람없음
			speed = sx;
			break;
		case 1 :				// 바람 →
			speed = Math.abs(sx) * 2;	
			break;
		case 2 :				// 바람 ←
			speed = -Math.abs(sx) * 2;
			break;
		default :				// 현재 바람 유지
			if (speed == 0) speed = sx;
		}

		x += speed;
		y += sy;
		if (x < -rad) x = width + rad; 		// 좌측을 벗어나면 우측에서 진입
		if (x > width + rad) x = -rad;		// 우측을 벗어나면 좌측에서 진입
		if (y > height + rad) y = -rad;		// 바닥을 벗어나면 천정에서 진입

		
		if (speed == sx) {					// Dir == 0 || 초기 상태
			dx += sx;						// 좌우로 이동한 거리 누적
			if (Math.abs(dx) > range) {		// 지정한 범위를 벗어나면 방향을 바꿈
				dx = 0;
				sx = -sx;
			}
		} // if
	} // move
}

//-----------------------------------------------------------

//-------------------------------------
// 비눗방울
//-------------------------------------
class Bubble {
	public int x, y, rad;			// 좌표, 반지름
	public  Bitmap imgBall;			// 비트맵 이미지
	public  boolean dead; 			// 터뜨림 여부
	
	private int sx, sy;				// 이동 방향 및 속도
	private int width, height;		// View의 크기
	private int imgNum;				// 이미지 번호
	private int loop;				// 루프 카운터
	private int counter;			// 벽과 충돌 회수
	private Random rnd;
	
	//-------------------------------------
	//  생성자
	//-------------------------------------
	public Bubble() {
		width = MyGameView.Width;
		height = MyGameView.Height;
		rnd = new Random();

	    rad = rnd.nextInt(21) + 20;		// 반지름 : 20 ~ 40;
	    imgBall = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), 
	    						R.drawable.bubble_1);
	    imgBall = Bitmap.createScaledBitmap(imgBall, rad * 2, rad * 2, false);
	    sx = rnd.nextInt(2) == 0 ? -2 : 2;
	    sy = rnd.nextInt(2) == 0 ? -4 : 4;
	    x = rnd.nextInt(width - 100) + 50;
	    y = rnd.nextInt(height - 100) + 50;
	    MoveBubble();
	}
	
	//-------------------------------------
	//  Move
	//-------------------------------------
	public void MoveBubble() {
		x += sx;
		y += sy;
		if (x <= rad || x >= width - rad) {		// 좌우로 충돌
			sx = -sx;
			x += sx;
			counter++;
		}	
		if (y <= rad || y >= height - rad) {	// 상하로 충돌 
			sy = -sy;
			y += sy;
			counter++;
		}
		if (counter >= 3) dead = true;		// 벽과 충돌 횟수
	}

} // Bubble

//------------------------------------------------------------

//-------------------------------------
// 작은 방울
//-------------------------------------
class SmallBall {
	public int x, y, rad;			// 좌표, 반지름
	public  boolean dead; 			// 터뜨림 여부
	public  Bitmap imgBall;			// 비트맵 이미지
	
	private int width, height;		// View의 크기
	private int cx, cy;				// 원의 중심점
	private int cr;					// 원의 반지름
	private double r;				// 이동 각도 (radian)
	private int speed;				// 이동 속도
	private int num;				// 이미지 번호
	private int life;				// 생명 주기
	
	//-------------------------------------
	//  생성자
	//-------------------------------------
	public SmallBall(int _x, int _y, int ang) {
		width = MyGameView.Width;
		height = MyGameView.Height;
		cx = _x;		// 중심점
		cy = _y;
		r = Math.toRadians(ang);		// 라디안으로 변환
		
		Random rnd = new Random();
		speed = rnd.nextInt(5) + 2;		// 속도     : 2~6
		rad = rnd.nextInt(6) + 3;		// 반지름   : 3~8
		num = rnd.nextInt(6);			// 이미지지  : 0~5
		life = rnd.nextInt(21) + 20;	// 20~40
		
		imgBall = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.b0 + num);
		imgBall = Bitmap.createScaledBitmap(imgBall, rad * 2, rad * 2, false);
		cr = 10;						// 원의 초기 반지름
		MoveBall();
	}

	//-------------------------------------
	//  MoveBall
	//-------------------------------------
	public void MoveBall() {
		life--;
		cr += speed;
		x = (int) (cx + Math.cos(r) * cr); 
		y = (int) (cy - Math.sin(r) * cr); 
		if (x < -rad || x > width + rad ||
				y < -rad || y > height + rad || life <= 0)
			dead = true;
	}
}

