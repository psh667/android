package com.corea.CriteriaLocDemo;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class CriteriaLocDemo extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		LocationManager locManager;															// 위치 메니져 생성 - location, locationprovider 얻을 수 있다.
		locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);				// 위치 매니져를 얻어온다.

		Criteria reqment = new Criteria();					// reqment를 사용하여, 최적의 provider을 찾아낼 수 있다.
		reqment.setAccuracy(Criteria.ACCURACY_COARSE);		// ACCURACY_FINE은 정확도 좋음 , ACCURACY_COARSE은 정확도 낮음
		reqment.setAltitudeRequired(false);				// 고도 사용 유무
		reqment.setBearingRequired(false);					// 방위 사용 유무
		reqment.setCostAllowed(true);						// 속도 사용 유무
		reqment.setPowerRequirement(Criteria.POWER_LOW);	// POWER_LOW 전력 소모 낮음, POWER_HIGH 전력 소모 높음. POWER_MEDIUM 전력 소모 보통 
		reqment.setSpeedRequired(false);					// 속도 사용 유무
		
		String pvder = locManager.getBestProvider(reqment, true);	// 위에 설정한 provider를 찾는다. 없으면 null 반환
		Location loc = locManager.getLastKnownLocation(pvder);	// 가지고 온 provider에서 전에 기록된 좌표가 있으면 가져와서

		/*
			Criteria클래스의 설정설정 부분은 SharedPreferences클래스를 이용하여 저장 하면 좋을 듯 합니다.
			
			이제 LocationManager와 LocationProvider까지 얻었으니얻었으니 이제 Location 클래스를 얻을 차례입니다.

		 */
		locUpdate(loc);									
		locManager.requestLocationUpdates(pvder, 2000, 10,			// 위치 갱신되는 조건
				locListener);
	}

	private final LocationListener locListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			locUpdate(location);
		}

		public void onProviderDisabled(String provider) {
			locUpdate(null);
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	/** Update UI with a new location */
	private void locUpdate(Location location) {
		TextView LocTV = (TextView) findViewById(R.id.LocTextView);

		String str;
		if (location != null) {
			double lat = location.getLatitude();							
			double lng = location.getLongitude();
			str = "  위도:" + lat + "\n  경도:" + lng;
		} else {
			str = "위치 찾지 못함.";
		}
		LocTV.setText("요구 기준에 의한 위치\n" + str);
	}
}
