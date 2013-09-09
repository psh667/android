package kr.co.infinity.ScaleTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

class MyView extends View {
	public MyView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);

		for (int i = 1; i <= 5; i++) {
			canvas.scale(1.5f, 1.5f);
			paint.setColor(0x88ff0000);
			canvas.drawCircle(50, 50, 20, paint);
		}
	}
}

public class ScaleTest extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}
}