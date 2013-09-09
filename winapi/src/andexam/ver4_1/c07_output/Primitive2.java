package andexam.ver4_1.c07_output;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class Primitive2 extends Activity {
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
			//canvas.drawARGB(255,204,204,204);
			//canvas.drawRGB(204,204,204);
			//canvas.drawColor(0xffcccccc);
			Paint Pnt = new Paint();
			Pnt.setColor(Color.LTGRAY);
			canvas.drawPaint(Pnt);
			// 빨간색 둥근 사각형
			RectF r = new RectF(10,10,100,100);
			Pnt.setColor(Color.RED);
			canvas.drawRoundRect(r,10,10,Pnt);
			// 타원
			r.set(110,10,150,100);
			canvas.drawOval(r,Pnt);
			// 분홍색 반달
			Pnt.setColor(Color.MAGENTA);
			r.set(10,110,100,200);
			canvas.drawArc(r,10,150,false,Pnt);
			// 분홍색 파이
			r.set(110,110,200,200);
			canvas.drawArc(r,10,150,true,Pnt);
			// 파란색 다각선
			Pnt.setColor(Color.BLUE);
			float[] pts={10,210,50,250,50,250,110,220};
			canvas.drawLines(pts,Pnt);
			// 검은색 점 3 개
			Pnt.setColor(Color.BLACK);
			float[] pts2={20,210,50,240,100,220};
			canvas.drawPoints(pts2, Pnt);
		}
	}
}
