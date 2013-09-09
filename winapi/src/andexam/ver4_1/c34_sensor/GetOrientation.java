package andexam.ver4_1.c34_sensor;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.hardware.*;
import android.os.*;
import android.widget.*;

public class GetOrientation extends Activity {
	SensorManager mSm;
	int mOrientCount;
	TextView mTxtOrient;
	float[] mGravity = null;
	float[] mGeoMagnetic = null;
	final static int FREQ = 20;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getorientation);

		mSm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mTxtOrient =(TextView)findViewById(R.id.result);
	}

	protected void onResume() {
		super.onResume();
		mSm.registerListener(mSensorListener, mSm.getDefaultSensor(
				Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
		mSm.registerListener(mSensorListener, mSm.getDefaultSensor(
				Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI);
	}

	protected void onPause() {
		super.onPause();
		mSm.unregisterListener(mSensorListener);
	}

	SensorEventListener mSensorListener = new SensorEventListener() {
		float[] mR = new float[9]; 
		float[] mI = new float[9];
		float[] mV = new float[3];
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		public void onSensorChanged(SensorEvent event) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER: 
				mGravity = event.values.clone(); 
				break; 
			case Sensor.TYPE_MAGNETIC_FIELD: 
				mGeoMagnetic = event.values.clone(); 
				break; 
			}

			// 너무 자주 전달되므로 빈도를 조절한다.
			if (mOrientCount++ % FREQ != 0) return;

			// 둘 다 조사되어 있을 때만
			if (mGravity != null && mGeoMagnetic != null) {
				SensorManager.getRotationMatrix(mR, mI, mGravity, mGeoMagnetic);
				float inclination = SensorManager.getInclination(mI);
				SensorManager.getOrientation(mR, mV);

				StringBuilder result = new StringBuilder();
				result.append("회수 = " + mOrientCount/FREQ + "회\n");
				result.append("Gra : " + dumpValues(mGravity));
				result.append("Mag : " + dumpValues(mGeoMagnetic));
				result.append("R:\n" + dumpMatrix(mR));
				result.append("I:\n" + dumpMatrix(mI));
				result.append("inclination:" + inclination);
				result.append("\nazimuth:" + String.format("%.2f, %.2f", 
						mV[0], Radian2Degree(mV[0])));
				result.append("\npitch:" + String.format("%.2f, %.2f", 
						mV[1], Radian2Degree(mV[1])));
				result.append("\nroll:" + String.format("%.2f, %.2f", 
						mV[2], Radian2Degree(mV[2])));

				mTxtOrient.setText(result.toString());
			}
		}

		String dumpValues(float[] v) {
			return String.format("%.2f, %.2f, %.2f\n", v[0], v[1], v[2]);
		}

		String dumpMatrix(float[] m) {
			return String.format("%.2f, %.2f, %.2f\n%.2f, %.2f, %.2f\n%.2f, %.2f, %.2f\n",
					m[0], m[1], m[2], m[3], m[4], m[5], m[6], m[7], m[8]);
		}

		float Radian2Degree(float radian) {
			return radian * 180 / (float)Math.PI;
		}
	};
}