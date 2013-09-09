package kr.co.company.mapcontrollertest;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;


public class MapControllerTest extends MapActivity {
	MapView map;
	MapController controller=null;
	double lattitude=37.0;
	double longitude=127.0;
	int level=8;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 현재 위치를 얻는다. 
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		    	lattitude = location.getLatitude();
		    	longitude = location.getLongitude();
		    	if( controller != null ){
		    		GeoPoint loc = new GeoPoint((int)(lattitude)*1000000, (int)(longitude)*1000000);
		    		controller.animateTo(loc);
		    	}
		    }
		    public void onStatusChanged(String provider, int status, Bundle extras) {}
		    public void onProviderEnabled(String provider) {}
		    public void onProviderDisabled(String provider) {}
		  };
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	
		map = (MapView)findViewById(R.id.mapview);
		map.setBuiltInZoomControls(true);

		controller = map.getController();
		controller.setZoom(level);

		GeoPoint myLocation = new GeoPoint((int)lattitude*1000000, (int)longitude*1000000);
		controller.setCenter(myLocation);
	}
	protected boolean isRouteDisplayed() {
		return false;
	}


	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0,1,0,"위성 모드 토글");
		menu.add(0,2,0,"교통 모드 토글");
		menu.add(0,3,0,"거리 모드 토글");
		menu.add(0,4,0,"줌인");
		menu.add(0,5,0,"줌아웃");
		menu.add(0,6,0,"현재 위치로 애니메이트하기");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			if( map.isSatellite() ) 
				map.setSatellite(false);
			else 
				map.setSatellite(true);
			return true;
		case 2:
			if( map.isTraffic() ) 
				map.setTraffic(false);
			else 
				map.setTraffic(true);
			return true;
		case 3:
			if( map.isStreetView() ) 
				map.setStreetView(false);
			else 
				map.setStreetView(true);
			return true;
		case 4:
			if( ++level > 21 ) level = 21;
			controller.setZoom(level);
			return true;
		case 5:
			if( --level < 1 ) level = 1;
			controller.setZoom(level);
			return true;
		case 6:
			GeoPoint loc = new GeoPoint((int)(lattitude)*1000000, (int)(longitude)*1000000);
			controller.animateTo(loc);
			return true;
		}
		return false;
	}
}