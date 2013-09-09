package kr.co.infinity.GradientTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;

class MyView extends View {
	public MyView(Context context) {
		super(context);
	}

	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();

		paint.setShader(new LinearGradient(0, 0, 100, 0, Color.WHITE,
				Color.BLUE, TileMode.CLAMP));
		canvas.drawRect(0, 0, 300, 50, paint);
		canvas.drawText("CLAMP", 0, 70, paint);

		paint.setShader(new LinearGradient(0, 0, 100, 0, Color.WHITE,
				Color.BLUE, TileMode.MIRROR));
		canvas.drawRect(0, 70, 300, 120, paint);
		canvas.drawText("MIRROR", 0, 140, paint);

		paint.setShader(new LinearGradient(0, 0, 100, 0, Color.WHITE,
				Color.BLUE, TileMode.REPEAT));
		canvas.drawRect(0, 140, 300, 190, paint);
		canvas.drawText("REPEAT", 0, 210, paint);

		int[] colors = { Color.WHITE, Color.RED, Color.BLUE };
		float[] positions = { 0.0f, 0.8f, 1.0f };

		paint.setShader(new LinearGradient(0, 0, 320, 0, colors, null,
				TileMode.CLAMP));
		canvas.drawRect(0, 210, 300, 260, paint);
		canvas.drawText("colors[]", 0, 280, paint);

		paint.setShader(new LinearGradient(0, 0, 320, 0, colors, positions,
				TileMode.CLAMP));
		canvas.drawRect(0, 280, 300, 330, paint);
		canvas.drawText("colors[]¿Í positions[]", 0, 350, paint);
	}
}

public class GradientTest extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}
}
