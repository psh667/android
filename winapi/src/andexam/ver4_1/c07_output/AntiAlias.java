package andexam.ver4_1.c07_output;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class AntiAlias extends Activity {
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
			Pnt.setColor(Color.BLACK);
			Pnt.setTextSize(60);

			// 기본 출력
			canvas.drawOval(new RectF(10,10,200,120), Pnt);
			canvas.drawText("Text", 210, 70, Pnt);
			
			// 안티 알리아스 적용
			Pnt.setAntiAlias(true);
			canvas.drawOval(new RectF(10,130,200,240), Pnt);
			canvas.drawText("Text", 210, 190, Pnt);
		}
	}
}
