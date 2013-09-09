package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class Dither extends Activity {
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

			Bitmap cup = BitmapFactory.decodeResource(getContext().getResources(), 
					R.drawable.cup);
			
			canvas.drawBitmap(cup, 20, 0, Pnt);
			Pnt.setDither(true);
			canvas.drawBitmap(cup, 40, 250, Pnt);
		}
	}
}