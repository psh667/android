package kr.co.infinity.LocationTest;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;


public class LocationTest extends Activity {

	TextView status;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		status = (TextView) findViewById(R.id.status);

		// 위치 관리자에 대한 참조값을 구한다.
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// 위치가 업데이트되면 호출되는 리스너를 정의한다.
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// 새로운 위치가 발견되면 위치 제공자에 의하여 호출된다.
				status.setText("위도; " + location.getLatitude() + "\n경도:"
						+ location.getLongitude() + "\n고도:"
						+ location.getAltitude());
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};

		// 위치를 업데이트 받기 위하여 리스너를 위치 관리자에 등록한다.
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);

	}
}