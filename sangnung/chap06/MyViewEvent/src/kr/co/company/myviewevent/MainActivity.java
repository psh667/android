package kr.co.company.myviewevent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

class MyView extends View {
	int key;
	String str;

	public MyView(Context context) {
		super(context);
		setBackgroundColor(Color.YELLOW);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		str = "" + keyCode;
		invalidate();
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		canvas.drawText("Å°ÄÚµå = " + str, 0, 20, paint);
	}
}

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MyView w = new MyView(this);
		w.setFocusable(true);
		setContentView(w);
	}
}