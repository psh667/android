package andexam.ver4_1.c07_output;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class TypeFace extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyView vw = new MyView(this);
		setContentView(vw);
	}

	class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.LTGRAY);
			Paint Pnt = new Paint();
			int y = 1;
			Pnt.setAntiAlias(true);
			Pnt.setTextSize(30);
			Pnt.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
			canvas.drawText("Font:Default", 10, y++ * 40, Pnt);
			Pnt.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL));
			canvas.drawText("Font:Default Bold", 10, y++ * 40, Pnt);
			Pnt.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
			canvas.drawText("Font:Monospace", 10, y++ * 40, Pnt);
			Pnt.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
			canvas.drawText("Font:Sans Serif", 10, y++ * 40, Pnt);
			Pnt.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
			canvas.drawText("Font:Serif", 10, y++ * 40, Pnt);
		}
	}
}
