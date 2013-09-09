package com.Project14;

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
    //  MyView     
    //----------------------------------
    class MyView extends View {
    	int width, height;			// View의 크기
    	int left, top;				// 왼쪽, 위 여백
    	int orgW, orgH;				// 원래의 이미지 크기
    	int picW, picH;				// 잘려진 조각의 크기	
    	Bitmap imgBack, imgOrg;		// 배경, 사진
    	Bitmap imgPic[][] = new Bitmap[5][3];   // 잘라진 사진 조각
    	
    	//----------------------------------
        //  생성자(Constructor)     
        //----------------------------------
		public MyView(Context context) {
			super(context);
			
			Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			width = display.getWidth();		// View의 가로 폭
			height = display.getHeight() - 50;   // View의 세로 높이
			
			// 배경 이미지를 읽고 View 크기에 맞게 늘림
			imgBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);
			imgBack = Bitmap.createScaledBitmap(imgBack, width, height, true);

			imgOrg = BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_1);
			orgW = imgOrg.getWidth();	// 원본의 크기
			orgH = imgOrg.getHeight();
			
			picW = orgW / 3;			// 사진 조각의 가로폭
			picH = orgH / 5;			// 사진 조각의 세로 높이

			left = (width - orgW) / 2;	// 왼쪽 여백
			top = (height - orgH) / 2;	// 위쪽 여백
			
			// 사진 자르기
			for (int i = 0; i < 5; i++)	{
				for (int j = 0; j < 3; j++)
					imgPic[i][j] = Bitmap.createBitmap(imgOrg, j * picW, i * picH, picW, picH);
			}
			// 맨 마지막 사진은 공란
			imgPic[4][2] = Bitmap.createBitmap(imgOrg, 0, 0, 1, 1);
			Shuffling();
		}
		
		//----------------------------------
        //  사진 조각 섞기     
        //----------------------------------
        private void Shuffling() {
        	Bitmap tmp;
        	int x, y, n;
        	Random rnd = new Random();
        	for (int i = 0; i < 5; i++) {
        		for (int j = 0; j < 3; j++) {
        			if (i == 4 && j == 2) break; 	// 맨 마지막 조각
        			n = rnd.nextInt(14);	// 0~13
        			y = n / 5;		// row
        			x = n % 3;		// col
        			tmp = imgPic[i][j];
        			imgPic[i][j] = imgPic[y][x];
        			imgPic[y][x] = tmp;
        		}
        	}
		}

		//----------------------------------
        //  onDraw     
        //----------------------------------
		public void onDraw(Canvas canvas) {
			Paint paint = new Paint();
			EmbossMaskFilter emboss = new EmbossMaskFilter(new float[] {1, 1, 1}, 0.5f, 1, 1);
			paint.setMaskFilter(emboss);
			
			canvas.drawBitmap(imgBack, 0, 0, null);
			for (int i = 0; i < 5; i++)	{
				for (int j = 0; j < 3; j++)
					canvas.drawBitmap(imgPic[i][j], left + j * picW, top + i * picH, paint); 
			}
		} // onDraw

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

