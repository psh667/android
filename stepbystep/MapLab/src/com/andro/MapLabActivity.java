package com.andro;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class MapLabActivity extends MapActivity implements LocationListener {
	MapView         mapView;
	MapController   mapCtrl; 
	LocationManager lm;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // main.xml의 MapView 클래스를 이용한 지도출력
        mapView = (MapView) findViewById(R.id.mapview);
        // 줌 컨트롤러 추가
        mapView.setBuiltInZoomControls(true);
        
        // 위치 설정을 위한 컨트롤러 생성
        mapCtrl = mapView.getController();
        // 지도의 확대
        mapCtrl.setZoom(16);
    }

    // 현재 위치의 위도 경도 (위치가 변하면 자동 조정됨)
    public void onLocationChanged(Location location) {
        GeoPoint geopt = new GeoPoint(
        		(int)(location.getLatitude()*1E6),
        		(int)(location.getLongitude()*1E6));
        
        mapCtrl.setCenter(geopt);
    }
    
    // MapActivity 클래스의 추상화 메소도로서 override 되어야 함
	@Override
    protected boolean isRouteDisplayed() {
        return false;
    }
	
	// 액티비티가 보여질 때 실행됨
    @Override
    public void onStart() {
        super.onStart();
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }
    
    // 액티비티가 보여지 않게 될 때 실행됨
    public void onStop() {
    	super.onStop();
    	lm.removeUpdates(this);
    }
    
    // LocationListener 클래스의 추상화 메소도로서 override 되어야 함
    public void onProviderEnabled(String provider) {
    }
    
    // LocationListener 클래스의 추상화 메소도로서 override 되어야 함
    public void onProviderDisabled(String provider) {
    }
    
    // LocationListener 클래스의 추상화 메소도로서 override 되어야 함
    public void onStatusChanged(String provider, int status, Bundle bd) {
    }
}