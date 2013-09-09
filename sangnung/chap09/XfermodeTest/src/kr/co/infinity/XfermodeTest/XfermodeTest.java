package kr.co.infinity.XfermodeTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.view.View;

class MyView extends View {
	private static final int W = 80;
	private static final int H = 80;
	private static final int ROW_MAX = 4; // number of samples per row

	private Bitmap mSrcB;
	private Bitmap mDstB;

	// create a bitmap with a circle, used for the "dst" image
	Bitmap makeDst(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

		p.setColor(0xFFFFCC44);
		c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
		return bm;
	}

	// create a bitmap with a rect, used for the "src" image
	Bitmap makeSrc(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

		p.setColor(0xFF66AAFF);
		c.drawRect(w / 3, h / 3, w * 19 / 20, h * 19 / 20, p);
		return bm;
	}

	public MyView(Context context) {
		super(context);

		mSrcB = makeSrc(W, H);
		mDstB = makeDst(W, H);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		Paint paint = new Paint();
		// paint.setFilterBitmap(false);

		paint.setXfermode(null);
		canvas.drawBitmap(mDstB, 0, 100, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
		canvas.drawBitmap(mSrcB, 0, 100, paint);

	}
}

public class XfermodeTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

}
