package andexam.ver4_1.c34_sensor;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.hardware.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MotionCounter extends Activity {
	final static String TAG = "MotionCounter";
	final static float G = SensorManager.STANDARD_GRAVITY;
	SensorManager mSm;
	TextView mCounterText;
	int mCounter = 100;
	float mPitch;
	float mRoll;
	long mApplyTime;
	int mSenGap = 1000;
	float mSenRange = 5.0f;

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.motioncounter);

		mSm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mSm.registerListener(mSensorListener, 
				mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
				SensorManager.SENSOR_DELAY_GAME);
		mSm.registerListener(mSensorListener, 
				mSm.getDefaultSensor(Sensor.TYPE_ORIENTATION), 
				SensorManager.SENSOR_DELAY_GAME);

		mCounterText = (TextView)findViewById(R.id.counter);
		UpdateValueText();
		
		findViewById(R.id.incsen).setOnClickListener(mClickListener);
		findViewById(R.id.decsen).setOnClickListener(mClickListener);
		findViewById(R.id.incgap).setOnClickListener(mClickListener);
		findViewById(R.id.decgap).setOnClickListener(mClickListener);
	}

	// 파괴되기 전까지 계속 동작해야 함
	protected void onDestroy() {
		super.onDestroy();
		mSm.unregisterListener(mSensorListener);
	}

	SensorEventListener mSensorListener = new SensorEventListener() {
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		float ax;
		float ay;
		@SuppressWarnings("deprecation")
		public void onSensorChanged(SensorEvent event) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ORIENTATION:
				mPitch = event.values[1];
				mRoll = event.values[2];
				break;
			case Sensor.TYPE_ACCELEROMETER:
				long now = System.currentTimeMillis();
				if (now - mApplyTime < mSenGap) break;

				ax = event.values[0];
				ax -= (float)Math.sin(Deg2Rad(mRoll)) * G;
				TextView txtresult = (TextView)findViewById(R.id.result);
				String result = "ax = " + ax;
				txtresult.setText(result);
				if (Math.abs(ax) > mSenRange) {
					mApplyTime = now;
					if (ax > 0) {
						mCounter++;
					} else {
						mCounter--;
					}
					mCounterText.setText("" + mCounter);
					break;
				}

				ay = event.values[1];
				ay += (float)Math.sin(Deg2Rad(mPitch)) * G;
				if (Math.abs(ay) > mSenRange) {
					mApplyTime = now;
					if (ay > 0) {
						mCounter = 100;
					} else {
						mCounter = 200;
					}
					mCounterText.setText("" + mCounter);
					break;
				}
				
				break;
			}
		}
	};

	float Deg2Rad(float deg) {
		return (deg * (float)Math.PI) / 180;
	}

	View.OnClickListener mClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.incsen:
			case R.id.decsen:
				mSenRange += (v.getId() == R.id.incsen ? 1.0f:-1.0f);
				break;
			case R.id.incgap:
			case R.id.decgap:
				mSenGap += (v.getId() == R.id.incgap ? 500:-500);
				break;
			}
			UpdateValueText();
		}
	};
	
	void UpdateValueText() {
		TextView txtsen = (TextView)findViewById(R.id.txtsen);
		txtsen.setText("민감도 : " + (int)mSenRange);
		TextView txtgap = (TextView)findViewById(R.id.txtgap);
		txtgap.setText("간격 : " + mSenGap);
	}
}
