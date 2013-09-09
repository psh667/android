package com.Project5;

import java.util.*;

import android.content.*;
import android.graphics.*;

//-------------------------------------
//  비눗방울
//-------------------------------------
public class Bubble {
	public int x, y, rad;			// 좌표, 반지름
	public  Bitmap imgBall;			// 비트맵 이미지
	public  boolean dead = false; 	// 터뜨림 여부
	
	private int _rad;				// 원래의 반지름
	private int sx, sy;				// 이동 방향 및 속도
	private int width, height;		// View의 크기
	private Bitmap Bubbles[] = new Bitmap[6];	// 풍선 애니메이션 용 이미지	
	private int imgNum = 0;			// 이미지 번호
	private int loop = 0;			// 루프 카운터
	
	//-------------------------------------
	//  생성자
	//-------------------------------------
	public Bubble(Context context, int _x, int _y, int _width, int _height) {
		width = _width;
		height = _height;
		x = _x;
		y = _y;
		
	    imgBall = BitmapFactory.decodeResource(context.getResources(), R.drawable.bubble_1);
	    
	    Random rnd = new Random();
	    _rad = rnd.nextInt(11) + 20;		// 반지름 : 20 ~ 30;
	    rad = _rad;
	    
	    // 반지름이 2 간격으로 커졌자 작아지는 공 6개 만들기
	    for (int i = 0; i <= 3; i++)
	    	Bubbles[i] = Bitmap.createScaledBitmap(imgBall, _rad * 2 + i * 2, _rad * 2 + i * 2, false);
	    Bubbles[4] = Bubbles[2];  
	    Bubbles[5] = Bubbles[1];
	    imgBall = Bubbles[0];
	    
	    sx = rnd.nextInt(2) == 0 ? -1 : 1;
	    sy = rnd.nextInt(2) == 0 ? -2 : 2;
	    MoveBubble();
	}
	
	//-------------------------------------
	//  Move
	//-------------------------------------
	public void MoveBubble() {
		loop++;
		if (loop % 3 == 0) {
			imgNum++;			// 비눗방울 번호
			if (imgNum > 5) imgNum = 0;
			imgBall = Bubbles[imgNum];
			
			// 비눗방울의 반지름 설정
			rad = _rad + (imgNum <= 3 ? imgNum : 6 - imgNum) * 2;   
		}
		
		x += sx;
		y += sy;
		if (x <= rad || x >= width - rad) {		// 좌우로 충돌
			sx = -sx;
			x += sx;
		}	
		if (y <= rad || y >= height - rad) {	// 상하로 충돌 
			sy = -sy;
			y += sy;
		}
	}
} // Bubble

//------------------------------------------------------------

//-------------------------------------
//  작은 방울
//-------------------------------------
class SmallBall {
	public int x, y, rad;			// 좌표, 반지름
	public  boolean dead = false; 	// 터뜨림 여부
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
	public SmallBall(Context context, int _x, int _y, int ang, int _width, int _height) {
		cx = _x;		// 중심점
		cy = _y;
		width = _width;
		height = _height;
		r = ang * Math.PI / 180;		// 각도 radian
		
		Random rnd = new Random();
		speed = rnd.nextInt(5) + 2;		// 속도     : 2~6
		rad = rnd.nextInt(6) + 2;		// 반지름   : 2~7
		num = rnd.nextInt(6);			// 이미지  : 0~5
		life = rnd.nextInt(31) + 20;	// 20~50
		
		imgBall = BitmapFactory.decodeResource(context.getResources(), R.drawable.b0 + num);
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

//------------------------------------------------------------

//-------------------------------------
//득점 표시 
//-------------------------------------
class Score {
	public int x, y, sw, sh;			// 좌표, 전체 이미지 크기
	public Bitmap imgScore;				// 글자 출력용 이미지

	private Bitmap fonts[] = new Bitmap[10];
	private int loop = 0;				// loop counter

	//-------------------------------------
	//  생성자
	//-------------------------------------
	public Score(Context context, int _x, int _y, int _score) {
		x = _x;	
		y = _y;
		for (int i = 0; i <= 9; i++)
			fonts[i] = BitmapFactory.decodeResource(context.getResources(), R.drawable.f0 + i);
		MakeScore(_score);
		Move();
	}

	//-------------------------------------
	//  점수 이미지
	//-------------------------------------
	public void MakeScore(int _score) {
		String score = "" + _score;			// 문자로 변환
		int L = score.length();				// 문자열의 길이

		// 빈 비트맵을 만든다
		imgScore = Bitmap.createBitmap(fonts[0].getWidth() * L, fonts[0].getHeight(), fonts[0].getConfig());

		// 비트맵에 이미지를 그려넣을 Canvas 생성
		Canvas canvas = new Canvas();
		// Canvas가 비트맵에 출력하도록 설정 - 이후의 모든 출력은 imgScore에 그려진다 
		canvas.setBitmap(imgScore);
	
		int w = fonts[0].getWidth();	 
		for (int i = 0; i < L; i++) {
			int n = (int) score.charAt(i) - 48;
			canvas.drawBitmap(fonts[n], w * i, 0, null); 
		}

		sw = imgScore.getWidth() / 2;
		sh = imgScore.getHeight() / 2;
	}

	//-------------------------------------
	//  Move
	//-------------------------------------
	public boolean Move() {
		y -= 4;
		if (y < -20)
			return false;
		else
			return true;
	}
}	

//------------------------------------------------------------

//-------------------------------------
//물방울 - 거미의 총알
//-------------------------------------
class WaterBall {
	public int x, y, rad;			// 좌표, 반지름
	public  boolean dead = false; 	// 제거 여부
	public  boolean hit = false; 	// 충돌여부
	public  Bitmap imgBall;			// 비트맵 이미지
	
	private int width, height;		// View의 크기
	private int speed;				// 이동 속도
	
	//-------------------------------------
	//  생성자
	//-------------------------------------
	public WaterBall(Context context, int _x, int _y, int _width, int _height) {
		x = _x;	
		y = _y;
		width = _width;
		height = _height;
	    imgBall = BitmapFactory.decodeResource(context.getResources(), R.drawable.w0);
	    rad = imgBall.getWidth() / 2;
	    speed = 8;
	    MoveBall();
	}

	//-------------------------------------
	//  Move Water
	//-------------------------------------
	public void MoveBall() {
		y -= speed;
		if (y < 0) dead = true;
	}
}

//------------------------------------------------------------

//-------------------------------------
// 거미
//-------------------------------------
class Spider {
	public int x, y, sw, sh;		// 좌표, 크기 
	public  boolean dead = false; 	// 사망
	public  boolean undead = true;	// 무적모드
	public  Bitmap imgSpider;		// 비트맵 이미지

	private Bitmap Spiders[] = new Bitmap[2];	// 무적모드를 위함
	
	private int width;				// View의 크기
	private int speed;				// 이동 속도
	
	private int loop = 0;			// 루프 카운터
	private int undeadCnt = 0;		// undead Mode 카운터
	private int imgNum = 1;			// undead Mode 이미지 번호
	
	//-------------------------------------
	//  생성자
	//-------------------------------------
	public Spider(Context context, int _x, int _y, int _width) {
		x = _x;	
		y = _y;
		width = _width;
	    Spiders[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spider1);
	    Spiders[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spider2);
		imgSpider = Spiders[0];
	    sw = imgSpider.getWidth() / 2;
	    sh = imgSpider.getHeight() / 2;
	    speed = 4;
	}

	//-------------------------------------
	//  무적 모드
	//-------------------------------------
	public void UndeadMode() {
		if (undeadCnt >= 5) return;
		loop++;
		if (loop % 5 == 0) {
			imgNum = 1 - imgNum; 	// 1, 0, 1, 0, ....
			imgSpider = Spiders[imgNum];
			undeadCnt++;
			if (undeadCnt >= 5) undead = false;	// 무적모드 해제
		}
	}

	//-------------------------------------
	//  Move Left
	//-------------------------------------
	public void MoveLeft() {
		if (x > sw) x -= speed;
		if (x < sw) x = sw;
	}
	
	//-------------------------------------
	//  Move Right
	//-------------------------------------
	public void MoveRight() {
		if (x < width - sw) x += speed;
		if (x > width - sw) x = width - sw;
	}
}

