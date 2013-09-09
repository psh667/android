package kr.co.company.customdrawable;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;

class MyView extends View {
	private ShapeDrawable mDrawable;

	public MyView(Context context) {
		super(context);

		int x = 10;
		int y = 10;
		int width = 300;
		int height = 200;

		mDrawable = new ShapeDrawable(new OvalShape());
		mDrawable.getPaint().setColor(Color.MAGENTA);
		mDrawable.setBounds(x, y, x + width, y + height);
	}

	protected void onDraw(Canvas canvas) {
		mDrawable.draw(canvas);
	}

}

public class CustomDrawableActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyView w = new MyView(this);
		setContentView(w);
	}
}