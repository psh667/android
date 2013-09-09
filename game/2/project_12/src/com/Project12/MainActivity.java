package com.Project12;

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
    // 다트 화살    
    //----------------------------------
    class Arrow {
    	public int x, y;   		// 화살 좌표 
    	public Bitmap dart; 	// 화살 이미지 
    	public int dh;			// 화살  size
    	
    	// 생성자 (Constructor)
    	public Arrow(int _x, int _y) {
    		dart = BitmapFactory.decodeResource(getResources(), 
    				R.drawable.dart);
			dh = dart.getHeight();
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
   		ArrayList<Arrow> mArrow;  	// 화살
    	Bitmap imgBack, imgTarget;	// 배경, 과녁
    	
		int arScore[] = {10, 6, 12, 4, 15, 8, 10, 6, 12, 4, 15, 8, 10};
		int score = 0;				 // 잠수
    	int tot = 0;                 // 점수 합계
    	
    	class Point {
    		int x, y;
    	}
    	Point pt = new Point();

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
			imgTarget = BitmapFactory.decodeResource(context.getResources(), R.drawable.target_2);
			imgTarget = Bitmap.createScaledBitmap(imgTarget, 280, 280, true);
			
			tw = imgTarget.getWidth() / 2;	// 과녁의 중심점
			th = imgTarget.getHeight() / 2;
			
	    	mArrow = new ArrayList<Arrow>();  // ArrayList 생성
	    	
	    	pt.x = 100;
	    	pt.y = 200;
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
			canvas.drawText("점수 = " + score, 10, 30, paint);
			//canvas.drawText("각도= " + deg, 200, 30, paint);
			canvas.drawText("합계 = " + pt.x, 200, 30, paint);
			canvas.drawBitmap(imgTarget, cx - tw, cy - th, null);
			for (Arrow tDart : mArrow) {
				canvas.drawBitmap(tDart.dart, tDart.x, tDart.y - tDart.dh, null);  
			}
		} // onDraw

		//------------------------------------
        //      onTouch Event
        //------------------------------------
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int x = (int) event.getX();
				int y = (int) event.getY();
				CalcScore(x, y);
				invalidate();	// onDraw() 호출 
			}
			return true;
		} // TouchEvent

		//------------------------------------
        //    점수 계산
        //------------------------------------
		private void CalcScore(int x, int y) {
			int r[] = {40, 90, 140};	// 원의 반지름
			
			// 각도 계산
			double deg = Math.atan2(x - cx,  y - cy) * 180 / Math.PI - 90;
			if (deg < 0) deg += 360;

			int n = 3;	// 맨 안쪽원은 점수의 3배			
			score = 0;
			// 어느 원 영역인지 조사
			for (int i = 0; i < 3; i++) {
				if (Math.pow(cx - x, 2) + Math.pow(cy - y, 2) <= Math.pow(r[i], 2)) {
					mArrow.add(new Arrow(x, y));	// 화살 추가
					for (int j = 0; j < 13; j++) {
						int k = j * 30 + 15;
						if (deg < k) {
							score = arScore[j] * n;		// 점수
							tot += score;				// 합계
							break;	
						}
					} // for j
					n--;
					if (score > 0) break;
				} // if
			} // for i
		} // CalcScore
		
    } // MyView

} // Activiry


