package com.appstudio.android.sample.ch_28_2;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.appstudio.android.sample.R;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;


public class MyLocationActivity extends MapActivity {
  MapView mMapView;
  CustomMyLocationOverlay mMyOverlay;
  
  @Override
  protected void onCreate(Bundle arg0) {
    super.onCreate(arg0);
    setContentView(R.layout.my_map_activity);
    mMapView = (MapView)findViewById(R.id.myGMap);
    MapController mapControl = mMapView.getController();
    mapControl.setZoom(13);
    mMapView.setBuiltInZoomControls(true);
    mMyOverlay = new CustomMyLocationOverlay(this, mMapView); 
    List<Overlay> overlays = mMapView.getOverlays();
    overlays.add(mMyOverlay);

    mMyOverlay.runOnFirstFix(new Runnable() {
      public void run() {
        mMapView.getController().
                 animateTo(mMyOverlay.getMyLocation());
      }
    });
  }

  @Override
  protected void onPause() {
    super.onPause();
    mMyOverlay.disableMyLocation();
    mMyOverlay.disableCompass();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mMyOverlay.enableMyLocation();
    mMyOverlay.enableCompass();
  }

  @Override
  protected boolean isRouteDisplayed() {
    return false;
  }
  
  class CustomMyLocationOverlay extends MyLocationOverlay {
    public CustomMyLocationOverlay(Context context, 
                                              MapView mapView) {
      super(context, mapView);
    }
    
    protected boolean dispatchTap() {
      Toast.makeText(MyLocationActivity.this, "현재 위치입니다.", 
                                     Toast.LENGTH_SHORT).show();
      return false;
    }
  }
}
