package com.Project1;

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
        
        // 전체 화면 모드
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 사용자 View 모드
        setContentView(new MyView(this));
    }
    
    //----------------------------------
    //  MyView     
    //----------------------------------
    class MyView extends View {
    	int width, height;		// View의 폭과 높이
        int cx, cy;   		    // 캐릭터의 현재 좌표
    	int rw, rh;             // 캐릭터의 중심점
    	Bitmap rabbit;
    	
    	
        //----------------------------------
        //  생성자(Constructor)     
        //----------------------------------
		public MyView(Context context) {
			super(context);
			Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			width = display.getWidth();		// View의 가로 폭
			height = display.getHeight();   // View의 세로 높이
			
			cx = width / 2;					// 화면의 중심점
			cy = height / 2;
			
			rabbit = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabbit_1);
			rw = rabbit.getWidth() / 2;		// 캐릭터의 중심점
			rh = rabbit.getHeight() / 2;
		}
		
        //----------------------------------
        //  onDraw     
        //----------------------------------
		public void onDraw(Canvas canvas) {
			canvas.drawBitmap(rabbit, cx - rw, cy - rh, null);
		}
    } // MyView
    
} // Activiry

