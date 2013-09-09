package kr.co.company.chap8lab;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
	private Paint[] mForegrounds = { makePaint(Color.BLACK),
			makePaint(Color.BLUE), makePaint(Color.GREEN), makePaint(Color.RED) };
	private static Random r = new Random();

	public MyView(Context context) {
		super(context);
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.YELLOW);
		int width = getWidth();
		int height = getHeight();
		for (int i = 0; i < 20; i++) {
			float x = r.nextInt(width);
			float y = r.nextInt(height);
			float radius = r.nextInt(80);
			Paint circleColor = mForegrounds[r.nextInt(mForegrounds.length)];
			canvas.drawCircle(x, y, radius, circleColor);
		}
	}

	private Paint makePaint(int color) {
		Paint p = new Paint();
		p.setColor(color);
		return (p);
	}
}