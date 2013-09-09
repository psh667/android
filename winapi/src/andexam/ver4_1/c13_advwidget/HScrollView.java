package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class HScrollView extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hscrollview);

		HorizontalScrollView svw = (HorizontalScrollView)findViewById(R.id.scr);
		svw.addView(new HColorView(this));
	}
}

class HColorView extends View {
	public HColorView(Context context) {
		super(context);
	}

	public void onDraw(Canvas canvas) {
		Paint Pnt = new Paint();
		for (int x = 0;x < 2560;x += 10) {
			Pnt.setARGB(255, 255 - x / 10, 255 - x / 10, 255);
			canvas.drawRect(x, 0, x + 10, 2560, Pnt);
		}
	}

	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(2560, 2560);
	}
}
