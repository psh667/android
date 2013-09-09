package andexam.ver4_1.c34_sensor;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.hardware.*;
import android.os.*;
import android.widget.*;

public class SensorManagerTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensormanagertest);

		String result = "";

		SensorManager sm = (SensorManager)getSystemService(
				Context.SENSOR_SERVICE);
		List<Sensor> arSensor = sm.getSensorList(Sensor.TYPE_ALL);
		result = "size = " + arSensor.size() + "\n\n";
		for (Sensor s : arSensor) {
			result += ("name = " + s.getName() + " ,type = " + s.getType() + 
					", vender = " + s.getVendor() + ", version = " + s.getVersion() + 
					", power = " + s.getPower() + ", res = " + s.getResolution() + 
					", range = " + s.getMaximumRange() + "\n\n");
		}

		TextView txtResult =(TextView)findViewById(R.id.result);
		txtResult.setText(result);
	}
}
