package com.andro;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class MapAdvLabActivity extends MapActivity implements LocationListener {
	MapView         mapView;
	MapController   mapCtrl; 
	LocationManager lm;	
	MyLocationOverlay myLocationOverlay;
	
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

        ///// 현재 위치를 지도에 표시함: 시작  
        // 현재 위치를 MapView에 출력하기 위한 MyLocationOverlay 클래스의 객체 생성 
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        // 맵뷰로부터 얻어지는 overlay 리스트를 List 클래스의 객체에 할당  
        List<Overlay>overlays = mapView.getOverlays();
        // overlay에 현재 위치를 추가함  
        overlays.add(myLocationOverlay);
        ///// 현재 위치를 지도에 표시함: 끝
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

    // 액티비티가 화면의 전면부에 있을 때 실행됨
    public void onResume() {
    	super.onResume();
    	// 현재 위치 표시
    	myLocationOverlay.enableMyLocation();
    	// 나침반 표시
    	myLocationOverlay.enableCompass();
    }
    
    // 액티비티가 보여지나 화면의 전면부에 있지 않을 때 실행됨
    public void onPause() {
    	super.onPause();
    	// 현재 위치 표시 않음  
    	myLocationOverlay.disableMyLocation();
    	// 나침반 표시 않음 
    	myLocationOverlay.disableCompass();
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