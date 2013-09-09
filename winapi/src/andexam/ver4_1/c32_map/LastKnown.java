package andexam.ver4_1.c32_map;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.location.*;
import android.os.*;
import android.widget.*;

public class LastKnown extends Activity {
	LocationManager mLocMan;
	TextView mResult;
	String mProvider;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lastknown);
		
		mLocMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mResult = (TextView)findViewById(R.id.result);
		
		mProvider = mLocMan.getBestProvider(new Criteria(), true);
		Location location = mLocMan.getLastKnownLocation(mProvider);

		String sloc;
		
		if (location == null) {
			sloc = "최근 위치 : 알수 없음";
		} else {
			sloc = String.format("최근 위치 : \n위도:%f\n경도:%f\n고도:%f",  
				location.getLatitude(), location.getLongitude(), location.getAltitude());
		}
		mResult.setText(sloc);
	}
}