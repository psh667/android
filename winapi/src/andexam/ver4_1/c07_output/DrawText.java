package andexam.ver4_1.c07_output;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class DrawText extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyView vw = new MyView(this);
		setContentView(vw);
	}

	class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.LTGRAY);
			Paint Pnt = new Paint();
			String str = "Made in Korea";
			char[] arCh = {'a', 'b', 'c' }; 
			
			// 기본 문자열 출력. 안티 알리아싱을 적용했다.
			Pnt.setAntiAlias(true);
			Pnt.setColor(Color.BLACK);
			canvas.drawText(str, 10, 20, Pnt);
			canvas.drawText(str, 2, 11, 10, 40, Pnt);
			canvas.drawText(arCh, 0, 3, 10, 60, Pnt);
				
			// 수평 정렬
			Pnt.setTextAlign(Paint.Align.LEFT);
			canvas.drawText("Left Align", 100, 90, Pnt);
			Pnt.setTextAlign(Paint.Align.CENTER);
			canvas.drawText("Center Align", 100, 110, Pnt);
			Pnt.setTextAlign(Paint.Align.RIGHT);
			canvas.drawText("Right Align", 100, 130, Pnt);
			
			// 수직 정렬은 항상 글꼴의 베이스에 맞춰진다.
			Pnt.setTextAlign(Paint.Align.LEFT);
			Pnt.setColor(Color.RED);
			canvas.drawText("Small", 10, 180, Pnt);
			Pnt.setTextSize(20);
			canvas.drawText("Mid", 40, 180, Pnt);
			Pnt.setTextSize(30);
			canvas.drawText("Big", 80, 180, Pnt);
			Pnt.setTextSize(40);
			canvas.drawText("Hy", 125, 180, Pnt);
			
			// 여러 가지 속성 동시에 적용해 보기
			Pnt.setColor(Color.BLUE);
			Pnt.setTextAlign(Paint.Align.LEFT);
			Pnt.setTextSize(30);
			Pnt.setTypeface(Typeface.create((String)null, Typeface.BOLD));
			Pnt.setUnderlineText(true);
			Pnt.setStrikeThruText(true);
			Pnt.setSubpixelText(true);
			Pnt.setTextSkewX(-0.25f);
			Pnt.setTextScaleX(0.8f);
			canvas.drawText(str, 10, 220, Pnt);
		}
	}
}
