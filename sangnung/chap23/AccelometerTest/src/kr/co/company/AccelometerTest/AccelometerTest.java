package kr.co.company.AccelometerTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

class MyView extends View {

	float x_accel = 0;
	float y_accel = 0;
	float z_accel = 0;

	public void setXAccel(float x) {
		this.x_accel = x;
	}

	public void setYAccel(float y) {
		this.y_accel = y;
	}

	public void setZAccel(float z) {
		this.z_accel = z;
	}

	public MyView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.YELLOW);
		paint.setTextSize(30);

		paint.setStrokeWidth((float) (Math.abs(z_accel) * 3.0));
		canvas.drawLine(200, 200, 200, 400, paint);
		canvas.drawText("" + z_accel, 200, 200, paint);

		paint.setStrokeWidth((float) (Math.abs(x_accel) * 3.0));
		canvas.drawLine(200, 400, 400, 400, paint);
		canvas.drawText("" + x_accel, 400, 400, paint);

		paint.setStrokeWidth((float) (Math.abs(y_accel) * 3.0));
		canvas.drawLine(100, 250, 200, 400, paint);
		canvas.drawText("" + y_accel, 100, 250, paint);

	}
}

public class AccelometerTest extends Activity implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mAccelometer;
	MyView view;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new MyView(this);
		setContentView(view);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			view.setXAccel(event.values[0]);
			view.setYAccel(event.values[1]);
			view.setZAccel(event.values[2]);
			view.invalidate();
		}
	}
}
