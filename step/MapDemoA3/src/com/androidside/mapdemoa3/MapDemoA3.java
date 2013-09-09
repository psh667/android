package com.androidside.mapdemoa3;

import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MapDemoA3 extends MapActivity {
    MapView mapView = null;
    MyLocationOverlay myLocationOverlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mapView = (MapView) findViewById(R.id.map);
        mapView.getController().setZoom(16);

        myLocationOverlay = new MyLocationOverlay(this, mapView);
        mapView.getOverlays().add(myLocationOverlay);

        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                mapView.getController().animateTo(
                        myLocationOverlay.getMyLocation());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        myLocationOverlay.enableCompass();
        myLocationOverlay.enableMyLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();

        myLocationOverlay.disableCompass();
        myLocationOverlay.getOrientation();
        myLocationOverlay.disableMyLocation();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}