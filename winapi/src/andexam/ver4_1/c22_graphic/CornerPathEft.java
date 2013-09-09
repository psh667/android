package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class CornerPathEft extends Activity {
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

			canvas.drawRect(10,10,300,90,Pnt);
			Pnt.setPathEffect(new CornerPathEffect(15));
			canvas.drawRect(10,100,300,190,Pnt);
			Pnt.setPathEffect(new CornerPathEffect(30));
			canvas.drawRect(10,200,300,290,Pnt);
		}
	}
}