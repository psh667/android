package org.androidtown.lbs.mapoverlay;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * 현재 위치의 지도를 보여주고 그 위에 오버레이를 추가하는 방법에 대해 알 수 있습니다.
 * 
 * Google APIs 중의 하나를 플랫폼으로 선택해야 합니다.
 * 인터넷 권한이 있어야 합니다.
 * uses-library를 사용해야 합니다.
 * 
 * @author Mike
 */
public class MainActivity extends MapActivity {

	MapView mapView;

	private GeoPoint geoPt;
	private List<Overlay> mapOverlays;
	private Drawable bankMarker;
	BankItemOverlay overlay;
	private MyLocationOverlay me;

	private CompassView mCompassView;
    private SensorManager mSensorManager;
    private boolean mCompassEnabled;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 메인 레이아웃 객체 참조
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainlayout);

        // 맵뷰 객체 참조
   	 	mapView = (MapView) findViewById (R.id.mapview);
   	 	mapView.setBuiltInZoomControls(true);
   	 	mapOverlays = mapView.getOverlays();

   	 	// 센서 관리자 객체 참조
   	 	mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

   	 	// 새로 정의한 오버레이 객체 생성하여 추가
   	 	me = new MyLocationOverlay(this, mapView);
		mapOverlays.add(me);

		// 나침반을 표시할 뷰 생성
		boolean sideBottom = true;
	   	mCompassView = new CompassView(this, sideBottom);
	    mCompassView.setVisibility(View.VISIBLE);

	    final RelativeLayout.LayoutParams compassParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	    compassParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	    compassParams.addRule(sideBottom ? RelativeLayout.ALIGN_PARENT_BOTTOM : RelativeLayout.ALIGN_PARENT_TOP);
	    mainLayout.addView(mCompassView, compassParams);

	    mCompassEnabled = true;
	    
	    // 위치 확인을 위해 정의한 메소드 호출
   	 	startLocationService();
    }

    @Override
	public void onResume() {
		super.onResume();

		// 내 위치 자동 표시 enable
		me.enableMyLocation();
		if(mCompassEnabled) {
            mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		// 내 위치 자동 표시 disable
		me.disableMyLocation();
		if(mCompassEnabled) {
			mSensorManager.unregisterListener(mListener);
		}
	}


    protected boolean isRouteDisplayed() {
		return false;
	}

    /**
     * 위치 확인을 위해 정의한 메소드
     */
    private void startLocationService() {
    	// 위치 관리자 객체 참조
    	LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    	// 리스너 객체 생성
    	GPSListener gpsListener = new GPSListener();
    	NetworkListener networkListener = new NetworkListener();
		long minTime = 10000;
		float minDistance = 0;

		// GPS 위치 요청
		manager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER,
					minTime,
					minDistance,
					gpsListener);
		
		// 네트워크 위치 요청
		manager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER,
					minTime,
					minDistance,
					networkListener);

		Toast.makeText(getApplicationContext(), "위치 확인 시작. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 리스너 정의
     */
	private class GPSListener implements LocationListener {
		/**
		 * 위치가 확인되면 호출되는 메소드
		 */
	    public void onLocationChanged(Location location) {
			Double latitude = location.getLatitude();
			Double longitude = location.getLongitude();

			String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
			Log.i("GPSListener", msg);
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

			// 현재 위치의 지도 보여주기
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
     * 리스너 정의
     */
	private class NetworkListener implements LocationListener {
		/**
		 * 위치가 확인되면 호출되는 메소드
		 */
	    public void onLocationChanged(Location location) {
			Double latitude = location.getLatitude();
			Double longitude = location.getLongitude();

			String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
			Log.i("NetworkListener", msg);
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

			// 현재 위치의 지도 보여주기
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
	 * 현재 위치의 지도 보여주기 위해 정의한 메소드
	 * 
	 * @param latitude
	 * @param longitude
	 */
	private void showCurrentLocation(Double latitude, Double longitude) {
		double intLatitude = latitude.doubleValue() * 1000000;
		double intLongitude = longitude.doubleValue() * 1000000;

		// 현재 위치를 GeoPoint 객체로 생성
		geoPt = new GeoPoint((int) intLatitude, (int) intLongitude);

		MapController controller = mapView.getController();
		controller.animateTo(geoPt);

		int maxZoomlevel = mapView.getMaxZoomLevel();
		int zoomLevel = (int) ((maxZoomlevel + 1)/1.15);
		controller.setZoom(zoomLevel);
		controller.setCenter(geoPt);

		mapView.setSatellite(false);
		mapView.setTraffic(false);

		// 현재 위치 주위에 아이콘을 표시하기 위해 정의한 메소드
		showAllBankItems();
	}

	/**
	 * 아이콘을 표시하기 위해 정의한 메소드
	 */
	private void showAllBankItems() {
		int ICON_BANK = R.drawable.bank;
		String msg = "● 지점명 : \n 국민은행(낙성대지점)\n"
					+ "● 주소 : \n 서울시 관악구 낙성대동";

		showItem(geoPt, ICON_BANK, "은행정보", msg);

	}

	/**
	 * 은행 아이콘 표시
	 * 
	 * @param aPoint
	 * @param markIcon
	 * @param title
	 * @param contents
	 */
	public void showItem(GeoPoint aPoint, int markIcon, String title, String contents) {
		if (mapOverlays == null) {
			mapOverlays = mapView.getOverlays();
		}

		if (bankMarker == null) {
			bankMarker = this.getResources().getDrawable(markIcon);
			bankMarker.setBounds(0, 0, bankMarker.getIntrinsicWidth(), bankMarker.getIntrinsicWidth());
		}

		GeoPoint bankPoint = new GeoPoint(aPoint.getLatitudeE6()+1000, aPoint.getLongitudeE6()+1000);

		overlay = new BankItemOverlay(bankMarker, this);
		OverlayItem overlayItem = new OverlayItem (bankPoint,  title,  contents);

		overlay.addOverlay(overlayItem);
		mapOverlays.add(overlay);

	}

	/**
	 * 센서의 정보를 받기 위한 리스너 객체 생성
	 */
	private final SensorEventListener mListener = new SensorEventListener() {
        private int iOrientation = -1;

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        // 센서의 값을 받을 수 있도록 호출되는 메소드
        public void onSensorChanged(SensorEvent event) {
            if (iOrientation < 0) {
                    iOrientation = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
            }
            
            mCompassView.setAzimuth(event.values[0] + 90 * iOrientation);
            mCompassView.invalidate();

        }

	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
