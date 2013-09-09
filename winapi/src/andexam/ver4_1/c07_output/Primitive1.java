package andexam.ver4_1.c07_output;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class Primitive1 extends Activity {
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
			// 검은색 점
			canvas.drawPoint(10,10,Pnt);
			// 파란색 선
			Pnt.setColor(Color.BLUE);
			canvas.drawLine(20,10,200,50,Pnt);
			// 빨간색 원
			Pnt.setColor(Color.RED);
			canvas.drawCircle(100,90,50,Pnt);
			// 반투명한 파란색 사각형
			Pnt.setColor(0x800000ff);
			canvas.drawRect(10,100,200,170,Pnt);
			// 검은색 문자열
			Pnt.setColor(Color.BLACK);
			canvas.drawText("Canvas Text Out", 10,200,Pnt);
		}
	}
}
