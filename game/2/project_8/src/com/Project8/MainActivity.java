package com.Project8;

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
    //  MyView     
    //----------------------------------
    class MyView extends View {
    	int width, height;		// View의 크기
    	int cx, cy;				// 회전축(중심점)			
    	int tw, th;				// 오뚝이의 회전축
    	int sw, sh;				// 그림자의 크기
    	int ang, dir;			// 현재의 각도, 회전 방향
    	int an1, an2; 			// 좌우의 한계점
    	
    	Bitmap imgBack, imgToy, imgShadow;

    	//----------------------------------
        //  생성자(Constructor)     
        //----------------------------------
		public MyView(Context context) {
			super(context);
			Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			width = display.getWidth();		// View의 가로 폭
			height = display.getHeight();   // View의 세로 높이
			
			cx = width / 2;
			cy = height / 2 + 100;		// View의 중심보다 아래로 이동 
			
			// 배경 이미지를 읽고 View 크기에 맞게 늘려줌
			imgBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);
			imgBack = Bitmap.createScaledBitmap(imgBack, width, height, true);
			
			// 오뚝이와 그림자 이미지 읽기
			imgToy = BitmapFactory.decodeResource(context.getResources(), R.drawable.toy);
			imgShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.shadow);
			
			tw = imgToy.getWidth() / 2;		// 캐릭터의 중심점
			th = imgToy.getHeight();		// 오뚝이의 중심은 오뚝이 하단부
			sw = imgShadow.getWidth() / 2;	// 그림자의 중심점
			sh = imgShadow.getHeight() / 2;
			
			ang = 0;			// 회전 각도
			dir = 0;			// 회전 방향
    		mHandler.sendEmptyMessageDelayed(0, 10);
		}
		
        //----------------------------------
        //  onDraw     
        //----------------------------------
		public void onDraw(Canvas canvas) {
			RotateToy();	// 회전 각 구하기
			
			// 배경과 그림자 그리기
			canvas.drawBitmap(imgBack, 0, 0, null);
			canvas.drawBitmap(imgShadow, cx - sw, cy - sh, null);
			
			canvas.rotate(ang, cx, cy);		// Canvas 회전
			canvas.drawBitmap(imgToy, cx - tw, cy - th, null);
			canvas.rotate(-ang, cx, cy);	// Canvas를 원래의 상태로 복구
		} // onDraw

        //----------------------------------
        //  오뚜기 회전하기     
        //----------------------------------
		private void RotateToy() {
			ang += dir;		   					// 각도 증가/감소
			if (ang <= an1 || ang >= an2) {		// 좌우의 한계점에 다다르면
				an1 ++;; 						// 왼쪽 증가
				an2 --;                        	// 오른쪽은 감소
				dir = -dir;						// 회전을 방향 반전시킴
				ang += dir;						// 벗어나기 이전 상태로
			}
		}

		//------------------------------------
        //      Timer Handler
        //------------------------------------
        Handler mHandler = new Handler() {          
        	public void handleMessage(Message msg) {
        		invalidate();	                       
        		mHandler.sendEmptyMessageDelayed(0, 10);
        	}
        }; // Handler

		//------------------------------------
        //      onTouch Event
        //------------------------------------
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				an1 = -15;			// 왼쪽 한계
				an2 = 15;			// 오른쪽 한계
				if (dir == 0)		       
					dir = -1;		// 맨 처음은 왼쪽으로 기울임
			}
			return true;
		}
		
    } // MyView

} // Activiry

