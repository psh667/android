package andexam.ver4_1.c07_output;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class Stroke extends Activity {
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

			// 캡 모양 테스트
			Pnt.setColor(Color.BLUE);
			Pnt.setStrokeWidth(16);
			canvas.drawLine(50,30,240,30,Pnt);
			Pnt.setStrokeCap(Paint.Cap.ROUND);
			canvas.drawLine(50,60,240,60,Pnt);
			Pnt.setStrokeCap(Paint.Cap.SQUARE);
			canvas.drawLine(50,90,240,90,Pnt);

			// 조인 모양 테스트
			Pnt.setColor(Color.BLACK);
			Pnt.setStrokeWidth(20);
			Pnt.setStyle(Paint.Style.STROKE);
			Pnt.setStrokeJoin(Paint.Join.MITER);
			canvas.drawRect(50,150,90,200,Pnt);
			Pnt.setStrokeJoin(Paint.Join.BEVEL);
			canvas.drawRect(120,150,160,200,Pnt);
			Pnt.setStrokeJoin(Paint.Join.ROUND);
			canvas.drawRect(190,150,230,200,Pnt);
		}
	}
}
