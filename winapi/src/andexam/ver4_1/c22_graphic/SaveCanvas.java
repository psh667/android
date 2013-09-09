package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class SaveCanvas extends Activity {
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
			canvas.drawRect(10,10,100,100,Pnt);

			Pnt.setColor(Color.GREEN);
			canvas.save();
			canvas.translate(200,0);
			canvas.drawRect(10,10,100,100,Pnt);

			Pnt.setColor(Color.BLUE);
			canvas.restore();
			canvas.translate(200,200);
			canvas.drawRect(10,10,100,100,Pnt);
		}
	}
}