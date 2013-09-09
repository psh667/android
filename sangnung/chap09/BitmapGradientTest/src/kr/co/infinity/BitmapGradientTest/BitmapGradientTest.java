package kr.co.infinity.BitmapGradientTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.SweepGradient;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;

class MyView extends View {
	public MyView(Context context) {
		super(context);
	}

	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();

		Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bitmap);
		paint.setShader(new BitmapShader(bitmap, TileMode.REPEAT, TileMode.REPEAT));
		canvas.drawRect(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight(), paint);

	}
}

public class BitmapGradientTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
    }
}