package andexam.ver4_1.c27_clipboard;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class DragShadow extends Activity {
	Button btnSource;
	Button btnTarget;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dragshadow);

		btnSource = (Button)findViewById(R.id.source);
		btnSource.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				ClipData clip = ClipData.newPlainText("dragtext", "dragtext");
				v.startDrag(clip, new CanvasShadow(v), null, 0);
				return false;
			}
		});

		btnTarget = (Button)findViewById(R.id.target);
		btnTarget.setOnDragListener(mDragListener);
	}

	class CanvasShadow extends View.DragShadowBuilder {
		int mWidth, mHeight;
		public CanvasShadow(View v) {
			super(v);
			mWidth = v.getWidth();
			mHeight = v.getHeight();
		}

		public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
			shadowSize.set(mWidth, mHeight);
			shadowTouchPoint.set(mWidth/3, mHeight/3);
		}

		public void onDrawShadow(Canvas canvas) {
			Paint pnt = new Paint();
			pnt.setColor(Color.YELLOW);
			canvas.drawRect(0,0,mWidth, mHeight, pnt);

			Paint pnt2 = new Paint();
			pnt2.setAntiAlias(true);
			pnt2.setColor(Color.RED);
			pnt2.setStrokeWidth(8);
			pnt2.setStyle(Paint.Style.STROKE);
			canvas.drawCircle(mWidth/2, mHeight/2, mHeight/2-5, pnt2);
		}
	}

	View.OnDragListener mDragListener = new View.OnDragListener() {
		public boolean onDrag(View v, DragEvent event) {
			Button btn;
			if (v instanceof Button) {
				btn = (Button)v;
			} else {
				return false;
			}

			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				if (event.getClipDescription().hasMimeType(
						ClipDescription.MIMETYPE_TEXT_PLAIN)) {
					btn.setText("Drop OK");
					return true;
				} else {
					return false;
				}
			case DragEvent.ACTION_DRAG_ENTERED:
				btn.setText("Enter");
				return true;
			case DragEvent.ACTION_DRAG_EXITED:
				btn.setText("Exit");
				return true;
			case DragEvent.ACTION_DROP:
				String text = event.getClipData().getItemAt(0).getText().toString();
				btn.setText(text);
				return true;
			case DragEvent.ACTION_DRAG_ENDED:
				if (event.getResult()) {
					Toast.makeText(DragShadow.this, "Drag & Drop completed", 0).show();
				} else {
					Toast.makeText(DragShadow.this, "Drag & Drop canceled", 0).show();
				}
				return true;
			}
			return true;
		}
	};
}
