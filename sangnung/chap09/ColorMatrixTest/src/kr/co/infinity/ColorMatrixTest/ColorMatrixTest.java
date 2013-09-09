package kr.co.infinity.ColorMatrixTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

class MyView extends View {
	Bitmap bitmap;

	public MyView(Context context) {
		super(context);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();

		canvas.drawBitmap(bitmap, 0, 0, paint);
		ColorMatrix colorm = new ColorMatrix(new float[] { -1.0f, 0, 0, 0, 255,
				0, -1.0f, 0, 0, 255, 0, 0, -1.0f, 0, 255, 0, 0, 0, 1, 0 });
		paint.setColorFilter(new ColorMatrixColorFilter(colorm));
		canvas.drawBitmap(bitmap, 0, 200, paint);
	}
}

public class ColorMatrixTest extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}
}