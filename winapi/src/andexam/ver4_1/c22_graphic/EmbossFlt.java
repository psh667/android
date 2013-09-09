package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class EmbossFlt extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

	protected class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.LTGRAY);
			Paint Pnt = new Paint();
			Pnt.setAntiAlias(true);

			Bitmap cup = BitmapFactory.decodeResource(getContext().getResources(), 
					R.drawable.cup);

			EmbossMaskFilter emboss = new EmbossMaskFilter(
					new float[] { 2, 2, 2 }, 0.5f, 6, 5);
			Pnt.setMaskFilter(emboss);
			canvas.drawBitmap(cup, 30, 30, Pnt);
		}
	}
}