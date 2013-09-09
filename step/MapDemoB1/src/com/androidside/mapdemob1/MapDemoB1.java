package com.androidside.mapdemob1;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MapDemoB1 extends MapActivity {
    private TextView latitudeText, longitudeText;

    private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mapView = (MapView) findViewById(R.id.mapview);

        mapView.getController().setZoom(16);
        mapView.setBuiltInZoomControls(true);

        latitudeText = (TextView) findViewById(R.id.latitude);
        longitudeText = (TextView) findViewById(R.id.longitude);

        // 어플이 실행될 때, 화면 중앙에 압정을 표시한다.
        GeoPoint centerGeoPoint = mapView.getMapCenter();
        
        // 마커를 이동한다.
        moveMarker(centerGeoPoint);
    }
    
    // 화면 중앙에 지정된 지오 포인트가 표시될 수 있도록 하며
    // addMarker 메소드를 호출해서 압정을 표시한다.
    private void moveMarker(GeoPoint centerGeoPoint) {
        mapView.getController().animateTo(centerGeoPoint);

        // 위도와 경도를 화면 상단에 표시한다.
        latitudeText.setText("Longitude: "
                + ((float) centerGeoPoint.getLongitudeE6() / 1000000));
        longitudeText.setText("Latitude: "
                + ((float) centerGeoPoint.getLatitudeE6() / 1000000));

        addMarker(centerGeoPoint.getLatitudeE6(), centerGeoPoint
                .getLongitudeE6());
    };

    // 압정을 화면에 표시한다.
    private void addMarker(int markerLatitude, int markerLongitude) {
        // 압정 이미지를 얻어 온다.
        Drawable marker = getResources().getDrawable(R.drawable.pin);
        marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
                .getIntrinsicHeight());

        mapView.getOverlays().add(
                new MyLocations(marker, markerLatitude, markerLongitude));
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }



    // 화면 표시물을 표현하고 관리하는 ItemizedOverlay 클래스를 작성한다.
    class MyLocations extends ItemizedOverlay<OverlayItem> {
        // 화면 표시물(압정들)을 관리하는 ArrayList 클래스
        private List<OverlayItem> locations = new ArrayList<OverlayItem>();
        private Drawable marker;
        private OverlayItem myOverlayItem;

        boolean isMove;

        // 주어진 위도, 경도를 OverlayItem으로 생성해서 locations에 추가한다.
        public MyLocations(Drawable marker, int latitudeE6,
                int longitudeE6) {
            super(marker);
            this.marker = marker;

            GeoPoint myPlace = new GeoPoint(latitudeE6, longitudeE6);
            
            myOverlayItem = new OverlayItem(myPlace, "위치", "현재 위치입니다");
            locations.add(myOverlayItem);            

            // createItem 메소드의 작업 전에 반드시 호출되어야 하며
            // ItemizedOverlay를 사용해서 아이템을 처리할 때 기반 작업을 해준다.
            populate();
        }

        @Override
        // 주어진 i로 locations에 저장된 OverlayItem을 반환한다.
        protected OverlayItem createItem(int i) {
            return locations.get(i);
        }

        @Override
        // locations의 전체 크기를 반환한다.
        public int size() {
            return locations.size();
        }
        
        @Override
        protected boolean onTap(int index) {            
            return super.onTap(index);
            
        }

        @Override
        // 압정(marker)을 화면에 그린다. 이때 이미지의 하단 중앙을 기준으로 그린다.
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {
            super.draw(canvas, mapView, shadow);
            
            boundCenterBottom(marker);
        }

        @Override
        // 사용자 이벤트에 기반해서 화면에 압정(marker)을 그린다.
        public boolean onTouchEvent(MotionEvent event, MapView map) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_UP) {
                if (!isMove) {
                    // 사용자가 선택한 곳의 지오포인트를 구한다.
                    Projection proj = mapView.getProjection();
                    GeoPoint loc = proj.fromPixels((int) event.getX(),
                            (int) event.getY());

                    // 기존 압정을 제거한다.
                    mapView.getOverlays().remove(0);

                    // 사용자가 선택한 위치에 압정을 새로 위치시킨다.
                    moveMarker(loc);
                }
            } else if (action == MotionEvent.ACTION_DOWN) {
                isMove = false;

            } else if (action == MotionEvent.ACTION_MOVE) {
                isMove = true;
            }

            return super.onTouchEvent(event, map);
        }
    }
}