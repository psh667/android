package kr.co.infinity.LightingColorTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
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

		LightingColorFilter filter = new LightingColorFilter(0xff0080, 0x000020);
		paint.setColorFilter(filter);
		canvas.drawBitmap(bitmap, 0, 200, paint);
	}
}

public class LightingColorTest extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}
}