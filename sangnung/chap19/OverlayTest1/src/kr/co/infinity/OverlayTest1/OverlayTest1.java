package kr.co.infinity.OverlayTest1;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;


public class OverlayTest1 extends MapActivity {
	MapView map;
	MapController controller=null;
	double lattitude=37.0;
	double longitude=127.0;
	int level=8;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		map = (MapView)findViewById(R.id.mapview);
		map.setBuiltInZoomControls(true);
		controller = map.getController();

		GeoPoint myLocation = new GeoPoint((int)lattitude*1000000, (int)longitude*1000000);
		controller.setCenter(myLocation);
		
		ImageView image = new ImageView(this);
		image.setImageResource(R.drawable.androidmarker);
		MapView.LayoutParams layout = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				myLocation, MapView.LayoutParams.CENTER);
		map.addView(image, layout);
	}
	protected boolean isRouteDisplayed() {
		return false;
	}
}