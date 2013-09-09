package andexam.ver4_1.c07_output;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class NewPaint extends Activity {
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
			Pnt.setAntiAlias(true);
			Pnt.setColor(Color.BLUE);
			Pnt.setStrokeWidth(10);
			Pnt.setStyle(Paint.Style.STROKE);
			Pnt.setStrokeJoin(Paint.Join.BEVEL);
			canvas.drawRect(10,10,300,100,Pnt);
		}
	}
}