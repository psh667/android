package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class Rotate extends Activity {
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

			Pnt.setColor(Color.YELLOW);
			canvas.drawRect(100,100,200,200,Pnt);

			Pnt.setColor(Color.GREEN);
			canvas.save();
			canvas.rotate(-30);
			canvas.drawRect(100,100,200,200,Pnt);

			Pnt.setColor(Color.BLUE);
			canvas.restore();
			canvas.rotate(30,150,150);
			canvas.drawRect(100,100,200,200,Pnt);
		}
	}
}