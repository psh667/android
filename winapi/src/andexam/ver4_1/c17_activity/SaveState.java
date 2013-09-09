package andexam.ver4_1.c17_activity;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class SaveState extends Activity {
	private MyView vw;
	int x;
	int y;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		x = 50;
		y = 50;
		vw = new MyView(this);
		vw.setFocusable(true);
		vw.setFocusableInTouchMode(true);
		setContentView(vw);
	}

	protected class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			Paint p = new Paint();
			p.setColor(Color.GREEN);
			canvas.drawCircle(x, y, 16, p);
		}

		public boolean onKeyDown(int KeyCode, KeyEvent event) {
			super.onKeyDown(KeyCode, event);
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				switch (KeyCode) {
				case KeyEvent.KEYCODE_DPAD_LEFT:
					x -= 15;
					invalidate();
					return true;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					x += 15;
					invalidate();
					return true;
				case KeyEvent.KEYCODE_DPAD_UP:
					y -= 15;
					invalidate();
					return true;
				case KeyEvent.KEYCODE_DPAD_DOWN:
					y += 15;
					invalidate();
					return true;
				}
			}
			return false;
		}

		public boolean onTouchEvent(MotionEvent event) {
			super.onTouchEvent(event);
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				x += 15;
				y += 15;
				invalidate();
				return true;
			}
			return false;
		}
	}
}