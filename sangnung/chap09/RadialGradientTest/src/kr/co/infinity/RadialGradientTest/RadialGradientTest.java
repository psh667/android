package kr.co.infinity.RadialGradientTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.View;

class MyView extends View {
	public MyView(Context context) {
		super(context);
	}

	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();

		paint.setShader(new RadialGradient(100, 100, 50, Color.RED, Color.YELLOW, TileMode.CLAMP));
		canvas.drawCircle(100, 100, 50, paint);

		paint.setShader(new RadialGradient(250, 100, 50, Color.YELLOW, Color.RED, TileMode.CLAMP));
		canvas.drawCircle(250, 100, 50, paint);

		paint.setShader(new SweepGradient(100, 250, Color.RED, Color.YELLOW));
		canvas.drawCircle(100, 250, 50, paint);

		paint.setShader(new SweepGradient(250, 250, Color.YELLOW, Color.RED));
		canvas.drawCircle(250, 250, 50, paint);

	}
}

public class RadialGradientTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
    }
}