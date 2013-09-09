package com.appstudio.android.sample.ch_10;

import java.util.List;

import com.appstudio.android.sample.R;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapViewActivity extends MapActivity  {
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mapviewmain);
	    
	    MapView mapView = (MapView) findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);
	    
	    List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
	    TestItemizedOverlay itemizedoverlay = new TestItemizedOverlay(drawable,getApplicationContext());
	    
	    GeoPoint point = new GeoPoint(37881311,127729968);
	    OverlayItem overlayitem = new OverlayItem(point, "Hello!", "Here is Seoul!");
	    
	    itemizedoverlay.addOverlay(overlayitem);
	    mapOverlays.add(itemizedoverlay);
	}
}
