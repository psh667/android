package andexam.ver4_1.c34_sensor;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.hardware.*;
import android.os.*;
import android.view.*;

public class Compass extends Activity {
	SensorManager mSm;
	CompassView mView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = new CompassView(this);
		setContentView(mView);

		mSm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	}

	@SuppressWarnings("deprecation")
	protected void onResume() {
		super.onResume();
		mSm.registerListener(mView, mSm.getDefaultSensor(Sensor.TYPE_ORIENTATION), 
				SensorManager.SENSOR_DELAY_UI);
	}

	protected void onPause() {
		super.onPause();
		mSm.unregisterListener(mView);
	}

	class CompassView extends View implements SensorEventListener {
		float azimuth;
		float pitch;
		float roll;
		final static int MAX = 30;
		Paint textPnt = new Paint();
		Bitmap compass;
		int width;
		int height;
		int w10;
		int h10;
		int thick;
		int length;

		public CompassView(Context context) {
			super(context);
			textPnt.setColor(Color.BLACK);
			textPnt.setTextSize(20);
			textPnt.setAntiAlias(true);
			compass = BitmapFactory.decodeResource(getResources(), R.drawable.compass);
		}

		public void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			width = w;
			height = h;
			w10 = width/10;
			h10 = height/10;
			thick = h10;
			length = w10 * 8;
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@SuppressWarnings("deprecation")
		public void onSensorChanged(SensorEvent event) {
			float[] v = event.values;
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ORIENTATION:
				if (azimuth != v[0] || pitch != v[1] || roll != v[2]) {
					azimuth = v[0];
					pitch = v[1];
					roll = v[2];
					invalidate();
				}
				break;
			}
		}

		public void onDraw(Canvas canvas) {
			Paint Pnt = new Paint();
			Pnt.setAntiAlias(true);

			// 수평, 수직 막대기 그림
			canvas.drawColor(Color.WHITE);
			Pnt.setColor(Color.BLACK);
			Rect pitchrt = new Rect(w10, h10, w10 + thick, h10 + length);
			canvas.drawRect(pitchrt, Pnt);
			Rect rollrt = new Rect(w10, h10*2 + length, w10*9, h10*2 + length + thick);
			canvas.drawRect(rollrt, Pnt);

			// 롤 값 표시
			float rollvalue = roll < -MAX ? -MAX:roll > MAX ? MAX:roll;
			int rollcenter = rollrt.left + rollrt.width()/2;
			int rolllength = rollrt.width() - thick;
			int rollpos = rollcenter + (int)(rolllength/2 * rollvalue / MAX);
			Pnt.setColor(rollvalue == 0 ? Color.RED:Color.YELLOW);
			canvas.drawCircle(rollpos, rollrt.top + thick/2, (int)(thick/2*0.9), Pnt);
			canvas.drawText("roll:" + (int)roll, rollrt.left, rollrt.top-5, textPnt);

			// 피치값 표시
			float pitchvalue = pitch;
			if ((Math.abs(pitch)) > 90) {
				pitchvalue = 180 - Math.abs(pitch);
				if (pitch < 0) pitchvalue *= -1; 
			}
			pitchvalue = pitchvalue < -MAX ? -MAX:pitchvalue > MAX ? MAX:pitchvalue;
			int pitchcenter = pitchrt.top + pitchrt.height()/2;
			int pitchlength = pitchrt.height() - thick;
			int pitchpos = pitchcenter + (int)(pitchlength/2 * pitchvalue / MAX);
			Pnt.setColor(pitchvalue == 0 ? Color.RED:Color.YELLOW);
			canvas.drawCircle(pitchrt.left + thick/2, pitchpos, (int)(thick/2*0.9), Pnt);
			canvas.drawText("pitch:" + (int)pitch, pitchrt.left, pitchrt.top-5, textPnt);

			// 나침반 표시
			Matrix m = new Matrix();
			m.postRotate(-azimuth, compass.getWidth()/2, compass.getHeight()/2);
			m.postTranslate(rollcenter - compass.getWidth()/2 + thick, 
					pitchcenter - compass.getHeight()/2);
			canvas.drawBitmap(compass, m, Pnt);
			canvas.drawText("azimuth:" + (int)azimuth, rollcenter, 
					pitchcenter - compass.getHeight()/2 - 5, textPnt);
		}
	}
}