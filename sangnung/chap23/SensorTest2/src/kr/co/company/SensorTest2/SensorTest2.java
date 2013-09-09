package kr.co.company.SensorTest2;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorTest2 extends Activity implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mOrientation;
	TextView text;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		text = (TextView) findViewById(R.id.text);
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
		float[] values = event.values;
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			text.setText("방향 센서값\n\n방위각: " + values[0] + "\n피치:" + values[1]
					+ "\n롤:" + values[2]);
		}
	}
}
