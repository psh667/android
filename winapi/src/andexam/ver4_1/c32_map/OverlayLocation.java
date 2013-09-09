package andexam.ver4_1.c32_map;

import andexam.ver4_1.*;
import java.util.*;

import android.content.*;
import android.os.*;
import android.widget.*;

import com.google.android.maps.*;

public class OverlayLocation extends MapActivity {
	MapView mMap;
	MyLocationOverlay2 mLocation;

	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapviewtest);

		mMap = (MapView)findViewById(R.id.mapview);
		MapController mapControl = mMap.getController();
		mapControl.setZoom(13);
		mMap.setBuiltInZoomControls(true);

		GeoPoint pt = new GeoPoint(37881311, 127729968);
		mapControl.setCenter(pt);

		mLocation = new MyLocationOverlay2(this, mMap); 
		List<Overlay> overlays = mMap.getOverlays();
		overlays.add(mLocation);

		mLocation.runOnFirstFix(new Runnable() {
			public void run() {
				mMap.getController().animateTo(mLocation.getMyLocation());
			}
		});
	}

	public void onResume() {
		super.onResume();
		mLocation.enableMyLocation();
		mLocation.enableCompass();
	}	

	public void onPause() {
		super.onPause();
		mLocation.disableMyLocation();
		mLocation.disableCompass();
	}

	class MyLocationOverlay2 extends MyLocationOverlay {
		public MyLocationOverlay2(Context context, MapView mapView) {
			super(context, mapView);
		}
		
		protected boolean dispatchTap() {
			Toast.makeText(OverlayLocation.this, "여기가 현재 위치입니다.", 
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}
}

