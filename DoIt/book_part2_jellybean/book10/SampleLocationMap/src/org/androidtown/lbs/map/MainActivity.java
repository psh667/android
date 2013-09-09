package org.androidtown.lbs.map;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

/**
 * 현재 위치의 지도를 보여주는 방법에 대해 알 수 있습니다.
 * 
 * Google APIs 중의 하나를 플랫폼으로 선택해야 합니다.
 * 인터넷 권한이 있어야 합니다.
 * uses-library를 사용해야 합니다.
 * 
 * @author Mike
 */
public class MainActivity extends MapActivity {

	MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 맵뷰 객체 참조
   	 	mapView = (MapView) findViewById (R.id.mapview);
   	 	mapView.setBuiltInZoomControls(true);

   	 	// 현재 위치 확인을 위해 정의한 메소드 호출
   	 	startLocationService();
    }

    protected boolean isRouteDisplayed() {
		return false;
	}

    /**
     * 현재 위치 확인을 위해 정의한 메소드
     */
    private void startLocationService() {
    	// 위치 관리자 객체 참조
    	LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// 리스너 객체 생성
    	GPSListener gpsListener = new GPSListener();
		long minTime = 10000;
		float minDistance = 0;

		// 위치 요청
		manager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER,
					minTime,
					minDistance,
					gpsListener);

		Toast.makeText(getApplicationContext(), "위치 확인 시작함. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 리스너 정의
     */
	private class GPSListener implements LocationListener {
		/**
		 * 위치 정보가 확인되었을 때 호출되는 메소드
		 */
	    public void onLocationChanged(Location location) {
			Double latitude = location.getLatitude();
			Double longitude = location.getLongitude();

			String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
			Log.i("GPSLocationService", msg);
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

			// 현재 위치의 지도를 보여주기 위해 정의한 메소드 호출
			showCurrentLocation(latitude, longitude);
		}

	    public void onProviderDisabled(String provider) {
	    }

	    public void onProviderEnabled(String provider) {
	    }

	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    }

	}

	/**
	 * 현재 위치의 지도를 보여주기 위해 정의한 메소드
	 * 
	 * @param latitude
	 * @param longitude
	 */
	private void showCurrentLocation(Double latitude, Double longitude) {
		double intLatitude = latitude.doubleValue() * 1000000;
		double intLongitude = longitude.doubleValue() * 1000000;

		// 현재 위치 좌표를 이용해 GeoPoint 객체 생성
		GeoPoint geoPt = new GeoPoint((int) intLatitude, (int) intLongitude);

		// 현재 위치로 이동하기 위해 콘트롤러 객체 생성
		MapController controller = mapView.getController();
		controller.animateTo(geoPt);

		int maxZoomlevel = mapView.getMaxZoomLevel();
		int zoomLevel = (int) ((maxZoomlevel + 1)/1.15);
		controller.setZoom(zoomLevel);
		controller.setCenter(geoPt);

		mapView.setSatellite(true);
		mapView.setTraffic(false);

	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
