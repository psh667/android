package kr.co.infinity.RotateTest;

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

		canvas.translate(150, 150);

		for(int i=0; i < 3; i++){
			canvas.rotate(120);
			canvas.drawRect(0, 0, 200, 100, paint);
		}
	}
}
public class RotateTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
    }
}

