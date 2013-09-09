package kr.co.company.keyevent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

public class MainActivity extends Activity {
	int x = 100, y = 100;

	protected class MyView extends View {
		public MyView(Context context) {
			super(context);
			setBackgroundColor(Color.YELLOW);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			Paint paint = new Paint();
			canvas.drawRect(x, y, x+50, y+50, paint);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyView w = new MyView(this);
		w.setFocusable(true);
		w.setFocusableInTouchMode(true);
		setContentView(w);
		w.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_UP) {
					switch (keyCode) {
					case KeyEvent.KEYCODE_J:
						x -= 30;
						break;
					case KeyEvent.KEYCODE_K:
						x += 30;
						break;
					}
					v.invalidate();
					return true;
				}
				return false;
			}
		});

	}
}
