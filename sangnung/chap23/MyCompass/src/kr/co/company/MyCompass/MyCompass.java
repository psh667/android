package kr.co.company.MyCompass;

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

class CompassView extends View {

	float azimuth = 0;
	float pitch = 0;
	float roll = 0;

	public void setAzimuth(float azimuth) {
		this.azimuth = azimuth;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public CompassView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.YELLOW);
		canvas.save();
		canvas.rotate(-azimuth, 250, 250);
		canvas.drawCircle(250, 250, 200, paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(50);
		canvas.drawText("N", 250, 80, paint);
		canvas.drawText("S", 250, 430, paint);
		canvas.drawRect(240, 80, 260, 430, paint);
		canvas.restore();
	}
}

public class MyCompass extends Activity implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mOrientation;
	CompassView compass;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		compass = new CompassView(this);
		setContentView(compass);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mOrientation,
				SensorManager.SENSOR_DELAY_UI);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			compass.setAzimuth(event.values[0]);
			compass.setPitch(event.values[1]);
			compass.setRoll(-event.values[2]);
			compass.invalidate();
		}
	}
}
