package kr.co.infinity.ConcatenationTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

class MyView extends View {
	Bitmap bitmap;
	public MyView(Context context) {
		super(context);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship);
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();

		canvas.save();
		canvas.translate(100, 50);
		canvas.rotate(45);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		canvas.restore();

		canvas.save();
		canvas.translate(200, 100);
		canvas.scale(0.3f, 0.3f);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		canvas.restore();
		
	}
}

public class ConcatenationTest extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}
}