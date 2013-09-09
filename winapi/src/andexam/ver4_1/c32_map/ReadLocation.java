package andexam.ver4_1.c32_map;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.location.*;
import android.os.*;
import android.widget.*;

public class ReadLocation extends Activity {
	LocationManager mLocMan;
	TextView mStatus;
	TextView mResult;
	String mProvider;
	int mCount;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readlocation);

		mLocMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mStatus = (TextView)findViewById(R.id.status);
		mResult = (TextView)findViewById(R.id.result);

		mProvider = mLocMan.getBestProvider(new Criteria(), true);
	}
	
	public void onResume() {
		super.onResume();
		mCount = 0;
		mLocMan.requestLocationUpdates(mProvider, 3000, 10, mListener);
		mStatus.setText("현재 상태 : 서비스 시작");
	}	
	
	public void onPause() {
		super.onPause();
		mLocMan.removeUpdates(mListener);
		mStatus.setText("현재 상태 : 서비스 정지");
	}

	LocationListener mListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			mCount++;
			String sloc = String.format("수신회수:%d\n위도:%f\n경도:%f\n고도:%f", mCount, 
					location.getLatitude(), location.getLongitude(), location.getAltitude());
			mResult.setText(sloc);
		}

		public void onProviderDisabled(String provider) {
			mStatus.setText("현재 상태 : 서비스 사용 불가");
		}

		public void onProviderEnabled(String provider) {
			mStatus.setText("현재 상태 : 서비스 사용 가능");
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			String sStatus = "";
			switch (status) {
			case LocationProvider.OUT_OF_SERVICE:
				sStatus = "범위 벗어남";
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				sStatus = "일시적 불능";
				break;
			case LocationProvider.AVAILABLE:
				sStatus = "사용 가능";
				break;
			}
			mStatus.setText(provider + "상태 변경 : " + sStatus);
		}
	};
}
