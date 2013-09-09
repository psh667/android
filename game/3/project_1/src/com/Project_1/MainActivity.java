package com.Project_1;

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
    //  비누방울     
    //----------------------------------
    class MyBubble  {
    	public int x, y, rad;			// 위치, 반지름
 	   	public Bitmap imgBbl;			// 비누방울 비트맵 이미지
 	   	public boolean dead = false;	// 터질것인지 여부

 	   	private int count = 0;			// 벽과의 충돌 회수
 	   	private int sx, sy;				// 이동 방향과 속도
 	   	private int width, height;		// View의 크기

 	   	//----------------------------------
 	   	//  생성자(Constructor)     
 	   	//----------------------------------
 	   	public 	MyBubble(int _x, int _y, int _width, int _height) {
 	   		x = _x;				// 파라메터 저장
 	   		y = _y;
 	   		width = _width;		// View의 크기
 	   		height = _height;
    		
 	   		Random rnd = new Random();  
 	   		rad = rnd.nextInt(31) + 10;					// 10~40 : 반지름
 	   		int k = rnd.nextInt(2) == 0 ? -1 : 1;		// -1, 1
 	   		sx = (rnd.nextInt(4) + 2) * k;				// ± 2~5 : 속도
 	   		sy = (rnd.nextInt(4) + 2) * k;				// ± 2~5

 	   		// 비트맵 이미지를 위에서 설정한 반지름 2배 크기로 만든다
 	   		imgBbl = BitmapFactory.decodeResource(getResources(), R.drawable.bubble);
 	   		imgBbl = Bitmap.createScaledBitmap(imgBbl, rad * 2, rad * 2, false);
 	   		MoveBubble();		// 비누방울 이동
 	   	}

 	   	//----------------------------------
 	   	//  비누방울 이동     
 	   	//----------------------------------
 	   	private void MoveBubble() {
 	   		x += sx;	// 이동
 	   		y += sy;
 	   		if (x <= rad || x >= width - rad) {		// 좌우의 벽
 	   			sx = -sx;							// 반대 방향으로 반사		
 	   			count++;							// 벽과 부딪친 횟수
 	   		}
 	   		if (y <= rad || y >= height - rad) {
 	   			sy = -sy;
 	   			count++;
 	   		}
 	   		if (count >= 3) dead = true;			// 벽과 2번 이상 충돌이면 터뜨림
 	   	}
    } // MyBubble
    
    //----------------------------------
    //  MyView     
    //----------------------------------
    class MyView extends View {
    	int width, height;		// View의 폭과 높이
 	   	Bitmap imgBack;
 	   	ArrayList<MyBubble> mBubble; 
    	
 	   	//----------------------------------
 	   	//  생성자(Constructor)     
 	   	//----------------------------------
 	   	public MyView(Context context) {
 	   		super(context);
 	   		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
 	   		width = display.getWidth();			// View의 가로 폭
 	   		height = display.getHeight() - 50;  // View의 세로 높이
			
 	   		imgBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);
 	   		imgBack = Bitmap.createScaledBitmap(imgBack, width, height, false);
			
 	   		mBubble = new ArrayList<MyBubble>();
 	   		mHandler.sendEmptyMessageDelayed(0, 10);  // Handler 호출
 	   	}
		
 	   	//----------------------------------
 	   	//  비누방울 이동     
 	   	//----------------------------------
 	   	private void MoveBubble() {
 	   		for (int i = mBubble.size() - 1; i >= 0; i--) {
 	   			mBubble.get(i).MoveBubble();
 	   			if (mBubble.get(i).dead == true)
 	   				mBubble.remove(i);
 	   		}
 	   	}

 	   	//------------------------------------
 	   	//   Touch가 비누방울 내부인지 조사
 	   	//------------------------------------
 	   	private void CheckBubble(int x, int y) {
 	   		boolean flag = false;
 	   		for (MyBubble tmp :  mBubble) {
 	   			if (Math.pow(tmp.x - x, 2) + Math.pow(tmp.y - y, 2) <= Math.pow(tmp.rad, 2)){
 	   				tmp.dead = true;
 	   				flag = true;
 	   			}
 	   		}
 	   		if (flag == false)	     // 비누방울 Touch가 아니면 비누방울 생성 
 	   			mBubble.add(new MyBubble(x, y, width, height));
 	   	}
        
       //----------------------------------
       //  onDraw     
       //----------------------------------
 	   	public void onDraw(Canvas canvas) {
 	   		MoveBubble();											// 비누방울 이동
 	   		canvas.drawBitmap(imgBack, 0, 0, null);		// 배경
 	   		for (MyBubble tmp :  mBubble) {					// 비누방울
 	   			canvas.drawBitmap(tmp.imgBbl, tmp.x - tmp.rad, 
													tmp.y - tmp.rad, null);
 	   		}
 	   	}
		
 	   	//------------------------------------
 	   	//      Timer Handler
 	   	//------------------------------------
 	   	Handler mHandler = new Handler() {          
 	   		public void handleMessage(Message msg) {
 	   			invalidate();		// View를 다시 그림                       
 	   			mHandler.sendEmptyMessageDelayed(0, 10);
 	   		}
 	   	}; // Handler
		
 	   	//------------------------------------
 	   	//      onTouch Event
        //------------------------------------
 	   	@Override
 	   	public boolean onTouchEvent(MotionEvent event) {
 	   		if (event.getAction() == MotionEvent.ACTION_DOWN){
 	   			int x = (int) event.getX();
 	   			int y = (int) event.getY();
 	   			CheckBubble(x, y);		// 비누방울 조사
 	   		}
 	   		return true;
 	   	} // onTouchEvent
 	   	
 	} // MyView
    
} // Activity
