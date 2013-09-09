package andexam.ver4_1.c34_sensor;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.Paint.*;
import android.hardware.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Ottogi extends Activity {
	AndroBoyView mView;
	OrientationEventListener mOrientationListener = null;
	int mOrientation = -1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = new AndroBoyView(this);
		setContentView(mView);

		mOrientationListener = new OrientationEventListener(this, 
				SensorManager.SENSOR_DELAY_FASTEST) {
			public void onOrientationChanged(int orientation) {
				mOrientation = orientation;
				mView.invalidate();
			}
		};
		mOrientationListener.enable();
	}

	class AndroBoyView extends View {
		int width;
		int height;
		Bitmap ottogi;

		public AndroBoyView(Context context) {
			super(context);
			ottogi = BitmapFactory.decodeResource(getResources(), R.drawable.ottogi);
		}

		public void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			width = w;
			height = h;
		}

		public void onDraw(Canvas canvas) {
			Paint Pnt = new Paint();
			Pnt.setAntiAlias(true);
			Pnt.setTextSize(30);
			Pnt.setTextAlign(Align.CENTER);

			canvas.drawColor(Color.WHITE);

			int cx = width/2;
			int cy = height/2;
			if (mOrientation != OrientationEventListener.ORIENTATION_UNKNOWN) {
				canvas.rotate(-mOrientation, cx, cy);
			}
			canvas.drawBitmap(ottogi, cx - ottogi.getWidth()/2, 
					cy - ottogi.getHeight()/2, Pnt);
			canvas.drawText("" + mOrientation, cx, cy, Pnt);

		}
	}
}
