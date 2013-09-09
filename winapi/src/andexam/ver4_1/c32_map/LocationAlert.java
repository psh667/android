package andexam.ver4_1.c32_map;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.location.*;
import android.os.*;

public class LocationAlert extends Activity {
	LocationManager mLocMan;
	PendingIntent mPending;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationalert);

		mLocMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Intent intent = new Intent(this, FishingReceiver.class);
		mPending = PendingIntent.getBroadcast(this, 0, intent, 0);
	}

	public void onResume() {
		super.onResume();
		mLocMan.addProximityAlert(37.94, 127.81, 500, -1, mPending);
	}	

	public void onPause() {
		super.onPause();
		mLocMan.removeProximityAlert(mPending);
	}
}

