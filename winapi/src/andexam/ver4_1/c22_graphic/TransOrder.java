package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class TransOrder extends Activity {
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
			canvas.drawRect(0,0,50,50,Pnt);

			canvas.save();
			Pnt.setColor(Color.GREEN);
			canvas.translate(100,100);
			canvas.scale(2, 2);
			canvas.drawRect(0,0,50,50,Pnt);

			canvas.restore();
			Pnt.setColor(Color.BLUE);
			canvas.scale(2, 2);
			canvas.translate(100,100);
			canvas.drawRect(0,0,50,50,Pnt);
		}
	}
}