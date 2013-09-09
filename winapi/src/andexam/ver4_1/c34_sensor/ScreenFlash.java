package andexam.ver4_1.c34_sensor;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.hardware.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class ScreenFlash extends Activity {
	SensorManager mSm;
	TextView mLight;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screenflash);

		mSm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mLight = (TextView)findViewById(R.id.lighttext);
	}

	protected void onResume() {
		super.onResume();
		mSm.registerListener(mListener, 
				mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void onPause() {
		super.onPause();
		mSm.unregisterListener(mListener);
	}

	SensorEventListener mListener = new SensorEventListener() {
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		public void onSensorChanged(SensorEvent event) {
			float z = event.values[2];
			Log.d("ScreenFlash", "z = " + z);
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			if (z > 0) {
				lp.screenBrightness = 0.1f;
				mLight.setBackgroundColor(Color.DKGRAY);
			} else {
				mLight.setBackgroundColor(Color.WHITE);
				lp.screenBrightness = Math.min(Math.max(0.1f, -z / 3), 1.0f);
			}
			getWindow().setAttributes(lp);
		}
	};
}
