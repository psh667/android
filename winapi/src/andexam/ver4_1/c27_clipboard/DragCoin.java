package andexam.ver4_1.c27_clipboard;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class DragCoin extends Activity {
	Button btnSource;
	LinearLayout uplinear, downlinear;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dragcoin);

		findViewById(R.id.coin500).setOnTouchListener(mTouchLintener);
		findViewById(R.id.coin100).setOnTouchListener(mTouchLintener);
		findViewById(R.id.coin50).setOnTouchListener(mTouchLintener);

		findViewById(R.id.uplinear).setOnDragListener(mDragListener);
		findViewById(R.id.downlinear).setOnDragListener(mDragListener);
	}

	View.OnTouchListener mTouchLintener = new View.OnTouchListener() {
		public boolean onTouch(View view, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				ClipData clip = ClipData.newPlainText("", "");
				view.startDrag(clip, new CanvasShadow(view, 
						(int)event.getX(), (int)event.getY()), view, 0);
				view.setVisibility(View.INVISIBLE);
				return true;
			}
			return false;
		}
	};

	class CanvasShadow extends View.DragShadowBuilder {
		int mWidth, mHeight;
		int mX, mY;
		public CanvasShadow(View v, int x, int y) {
			super(v);
			mWidth = v.getWidth();
			mHeight = v.getHeight();
			mX = x;
			mY = y;
		}

		public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
			shadowSize.set(mWidth, mHeight);
			shadowTouchPoint.set(mX, mY);
		}

		public void onDrawShadow(Canvas canvas) {
			super.onDrawShadow(canvas);
		}
	}
	
	View.OnDragListener mDragListener = new View.OnDragListener() {
		public boolean onDrag(View v, DragEvent event) {
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				return true;
			case DragEvent.ACTION_DRAG_ENTERED:
				return true;
			case DragEvent.ACTION_DRAG_EXITED:
				return true;
			case DragEvent.ACTION_DROP:
				View view = (View)event.getLocalState();
				ViewGroup parent = (ViewGroup)view.getParent();
				parent.removeView(view);
				LinearLayout newparent = (LinearLayout)v;
				newparent.addView(view);
				view.setVisibility(View.VISIBLE);
				return true;
			case DragEvent.ACTION_DRAG_ENDED:
				((View)(event.getLocalState())).setVisibility(View.VISIBLE);
				return true;
			}
			return true;
		}
	};
}
