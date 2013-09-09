package com.andro;

import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class MapBasicActivity extends MapActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // mani.xml의 MapView 클래스를 이용한 지도출력   
        MapView mapView = (MapView) findViewById(R.id.mapview);

        // 지도에 줌 컨트롤러 추가 
        mapView.setBuiltInZoomControls(true);
        
        // 서울 덕수궁 위치(위도, 경도)
        GeoPoint geopt = new GeoPoint((int)(37.565467*1E6), (int)(126.975431*1E6));
        
        // 위치 설정을 위한 컨트롤러 생성
        MapController mapCtrl = mapView.getController();
        // 지도의 확대(1~21)
        mapCtrl.setZoom(16);
        // 지정한 위치의 지도 중심 설정
        mapCtrl.setCenter(geopt);
    }
    
    // MapActivity 클래스의 추상화 메소도로서 override 되어야 함
	@Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}