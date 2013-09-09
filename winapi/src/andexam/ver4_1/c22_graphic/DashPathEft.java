package andexam.ver4_1.c22_graphic;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class DashPathEft extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

	protected class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			Paint Pnt = new Paint();
			Pnt.setAntiAlias(true);

			Pnt.setAntiAlias(false);
			Pnt.setColor(Color.WHITE);
			Pnt.setStrokeWidth(3);
			Pnt.setPathEffect(new DashPathEffect(new float[] {10, 10 }, 0));
			canvas.drawLine(10,10,300,10,Pnt);
			Pnt.setPathEffect(new DashPathEffect(new float[] {10, 10 }, 3));
			canvas.drawLine(10,30,300,30,Pnt);
			Pnt.setStrokeWidth(6);
			canvas.drawLine(10,50,300,50,Pnt);
			Pnt.setStrokeWidth(3);
			Pnt.setPathEffect(new DashPathEffect(new float[] {2, 2 }, 0));
			canvas.drawLine(10,70,300,70,Pnt);
			Pnt.setPathEffect(new DashPathEffect(new float[] {12, 2, 2, 2 }, 0));
			canvas.drawLine(10,90,300,90,Pnt);
			Pnt.setPathEffect(new DashPathEffect(new float[] {12, 2, 2, 2, 2, 2 }, 0));
			canvas.drawLine(10,110,300,110,Pnt);
		}
	}
}