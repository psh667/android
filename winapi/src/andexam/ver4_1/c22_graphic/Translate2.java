package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class Translate2 extends Activity {
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
			Pnt.setTextSize(30);

			Pnt.setColor(Color.WHITE);
			String[] arStr = { "One", "Two", "Three", "Four", "Five", "Six" };

			for (String s : arStr) {
				canvas.drawText(s, 10, 30, Pnt);
				canvas.translate(0,40);
			}
		}
	}
}