package com.Project11;

import java.util.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }
    
    //----------------------------------
    // 총알 구멍    
    //----------------------------------
    class Bullet {
    	public int x, y;    // 총알구멍 좌표 
    	public Bitmap hole; // 총알구멍 이미지 
    	public int bw, bh;	// 총알구멍  size
    	
    	public Bullet(int _x, int _y) {
			hole = BitmapFactory.decodeResource(getResources(), R.drawable.hole);
			bw = hole.getWidth() / 2;
			bh = hole.getHeight() / 2;
			x = _x;
			y = _y;
    	}
    }
    
    //----------------------------------
    //  MyView     
    //----------------------------------
    class MyView extends View {
    	int width, height;			// View의 크기
    	int cx, cy;					// View의 중심
    	int tw, th;					// 과녁 중심
    	ArrayList<Bullet> mBullet;  // 총알 구멍
    	Bitmap imgBack, imgTarget;	// 배경, 과녁
    	
    	int Score[] = {10, 8, 6, 0};  // 과녁의 점수
    	int n = 3;                    // 과녁 배열 첨자  
    	int tot = 0;                  // 점수 합계

    	//----------------------------------
        //  생성자(Constructor)     
        //----------------------------------
		public MyView(Context context) {
			super(context);
			
			Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			width = display.getWidth();		// View의 가로 폭
			height = display.getHeight();   // View의 세로 높이
			
			cx = width / 2;
			cy = height / 2 - 30;			// View의 중심보다 위로 이동 
			
			// 배경 이미지를 읽고 View 크기에 맞게 늘려줌
			imgBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);
			imgBack = Bitmap.createScaledBitmap(imgBack, width, height, true);
			
			// 과녁의 이미지를 280x280으로 설정
			imgTarget = BitmapFactory.decodeResource(context.getResources(), R.drawable.target_1);
			imgTarget = Bitmap.createScaledBitmap(imgTarget, 280, 280, true);
			
			tw = imgTarget.getWidth() / 2;	// 과녁의 중심점
			th = imgTarget.getHeight() / 2;
			
	    	mBullet = new ArrayList<Bullet>();  // ArrayList 생성
		}
		
        //----------------------------------
        //  onDraw     
        //----------------------------------
		public void onDraw(Canvas canvas) {
			Paint paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setTextSize(18);
			// 배경
			canvas.drawBitmap(imgBack, 0, 0, null);
			// 점수
			canvas.drawText("점수 = " + Score[n], 10, 30, paint);
			canvas.drawText("합계 = " + tot, 200, 30, paint);
			canvas.drawBitmap(imgTarget, cx - tw, cy - th, null);
			for (Bullet tBullet : mBullet) {
				canvas.drawBitmap(tBullet.hole, tBullet.x - tBullet.bw, tBullet.y - tBullet.bh, null);  
			}
		} // onDraw

		//------------------------------------
        //      onTouch Event
        //------------------------------------
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			int r[] = {40, 90, 140};
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int x = (int) event.getX();
				int y = (int) event.getY();
				
				n = 3;
				// 어느 원 영역인지 조사
				for (int i = 0; i < 3; i++) {
					if (Math.pow(cx - x, 2) + Math.pow(cy - y, 2) <= Math.pow(r[i], 2)) {
						mBullet.add(new Bullet(x, y));
						n = i;
						tot += Score[n];	// 총점
						break;
					}
				} // for
			}
			invalidate();	// onDraw() 호출 
			return true;
		}
		
    } // MyView

	//------------------------------------
    //     onKeyDown
    //------------------------------------
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 아무 키나 누르면 프로그램 종료
		System.exit(0);
		return true;
	}

} // Activiry

