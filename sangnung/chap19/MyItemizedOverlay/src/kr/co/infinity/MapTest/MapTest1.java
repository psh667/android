package kr.co.infinity.MapTest;

import java.util.List;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapTest1 extends MapActivity {
	/** Called when the activity is first created. */
	GeoPoint point = new GeoPoint(19240000,-99120000);
	OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.androidmarker);
		MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable);
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}