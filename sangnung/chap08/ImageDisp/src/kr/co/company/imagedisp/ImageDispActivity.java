package kr.co.company.imagedisp;

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
	public MyView(Context context) {
		super(context);
		setBackgroundColor(Color.YELLOW);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		Bitmap b = BitmapFactory.decodeResource(getResources(),
				R.drawable.android);
		canvas.drawBitmap(b, 0, 0, null);
	}
}

public class ImageDispActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyView w = new MyView(this);
		setContentView(w);
	}
}