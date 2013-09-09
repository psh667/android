package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class DashAnim extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

	protected class MyView extends View {
		Path dash;
		Paint Pnt;
		int phase;
		RectF ovalrt;

		public MyView(Context context) {
			super(context);

			dash = new Path();
			dash.moveTo(0, -4);
			dash.lineTo(4, -4);
			dash.lineTo(4, -8);
			dash.lineTo(10, 0);
			dash.lineTo(4, 8);
			dash.lineTo(4, 4);
			dash.lineTo(0, 4);

			Pnt = new Paint();
			Pnt.setAntiAlias(true);
			Pnt.setColor(Color.WHITE);
			phase = 0;
			ovalrt = new RectF(10,10,200,150);
			
			mAnimHandler.sendEmptyMessageDelayed(0, 100);
		}

		public void onDraw(Canvas canvas) {
			PathDashPathEffect pathdash = new PathDashPathEffect(dash, 14, phase, 
					PathDashPathEffect.Style.ROTATE); 
			Pnt.setPathEffect(pathdash);
			canvas.drawOval(ovalrt, Pnt);
		}
		
		Handler mAnimHandler = new Handler() {
			public void handleMessage(Message msg) {
				phase--;
				invalidate();
				sendEmptyMessageDelayed(0, 100);
			}
		};
	}
}