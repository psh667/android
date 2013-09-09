package com.Project6;

import android.app.*;
import android.content.*;
import android.content.res.*;
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

    	//----------------------------------
        //  생성자(Constructor)     
        //----------------------------------
		public MyView(Context context) {
			super(context);
			
		}
		
        //----------------------------------
        //  onDraw     
        //----------------------------------
		public void onDraw(Canvas canvas) {
			int cx = getWidth() / 2;       // View 의 중심점
			int cy = getHeight() / 2;
			int w = 0;                 
			int h = 0;
			int DIRECTION = 4; 	    // 이미지 선택 (장미 1~4)

			Bitmap rose[] = new Bitmap[4];
			Resources res = getResources();

			rose[0] = BitmapFactory.decodeResource(res, R.drawable.rose_1);
			rose[1] = BitmapFactory.decodeResource(res, R.drawable.rose_2);
			rose[2] = BitmapFactory.decodeResource(res, R.drawable.rose_3);
			rose[3] = BitmapFactory.decodeResource(res, R.drawable.rose_4);
			
			Paint paint = new Paint();
			paint.setColor(Color.RED);                
			paint.setStyle(Paint.Style.STROKE);	// 외곽선만 그린다

			canvas.drawColor(Color.WHITE);		// View를 흰색으로 채우기
			switch (DIRECTION) {
			case 1 :  // ↑ 
				w = rose[0].getWidth() / 2;		// 회전축 계산
				h =rose[0].getHeight();
				break;
			case 2 :  // → 
				w = 0;
				h = rose[1].getHeight() / 2;
				break;
			case 3 :  // ↓ 
				w = rose[2].getWidth() / 2;
				h = 0;
				break;
			case 4 :  // ← 
				w = rose[3].getWidth();
				h = rose[3].getHeight() / 2;
			} // switch
			
			// 프로그램 타이틀 변경
			getWindow().setTitle("장미꽃 회전  " + DIRECTION);  
	   
			// 10, 10 위치에 원래의 이미지 출력
			canvas.drawBitmap(rose[DIRECTION - 1], 10, 10, null);
			// 회전축을 빨간 원으로 표시
			canvas.drawCircle(w + 10, h + 10, 10, paint);	
	   
			// 20도 간격으로 회전하면 18번 반복
			for (int i = 1; i <= 18; i++) {
				// Canvas를 중심점을 회전축으로 20도 회전
				canvas.rotate(20, cx, cy);
				// 중심점에 이미지 끝이 오도록 출력
	            canvas.drawBitmap(rose[DIRECTION - 1], cx - w, cy - h, null);    
	       } // for
		} // onDraw

    } // MyView

} // Activiry

