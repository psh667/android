package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class PathDashEft extends Activity {
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

			Pnt.setColor(Color.WHITE);

			Path dash = new Path();
			dash.moveTo(0, -4);
			dash.lineTo(4, -4);
			dash.lineTo(4, -8);
			dash.lineTo(10, 0);
			dash.lineTo(4, 8);
			dash.lineTo(4, 4);
			dash.lineTo(0, 4);
			
			PathDashPathEffect pathdash = new PathDashPathEffect(dash, 14, 0, 
					PathDashPathEffect.Style.ROTATE); 
			Pnt.setPathEffect(pathdash);
			canvas.drawLine(10,10,10,200,Pnt);
			canvas.drawCircle(180, 100, 80, Pnt);
			
			ComposePathEffect comp = new ComposePathEffect(pathdash, 
					new CornerPathEffect(16));
			Pnt.setPathEffect(comp);
			canvas.drawRect(50, 200, 250, 300, Pnt);
		}
	}
}