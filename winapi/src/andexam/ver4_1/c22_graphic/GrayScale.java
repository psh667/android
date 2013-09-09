package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class GrayScale extends Activity {
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

			ColorMatrix cm = new ColorMatrix(
					new float[] {
							0.299f, 0.587f, 0.114f, 0, 0,
							0.299f, 0.587f, 0.114f, 0, 0,
							0.299f, 0.587f, 0.114f, 0, 0,
							0, 0, 0, 1, 0 }
			);

			Pnt.setColorFilter(new ColorMatrixColorFilter(cm));
			canvas.drawBitmap(cup, 30, 30, Pnt);
		}
	}
}