package kr.co.infinity.MaskTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
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

		BlurMaskFilter blur_filter = new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);
		paint.setMaskFilter(blur_filter);
		canvas.drawBitmap(bitmap, 0, 200, paint);

		EmbossMaskFilter emboss_filter = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.3f, 10, 10);
		paint.setMaskFilter(emboss_filter);
		canvas.drawBitmap(bitmap, 0, 200, paint);
	}
}

public class MaskTest extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}
}