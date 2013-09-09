package com.paad.whereami;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class WhereAmI extends MapActivity {

	MapController mapController;
	MyPositionOverlay positionOverlay;
	
	@Override
    protected boolean isRouteDisplayed() {
        return false;
    }
	
	private final LocationListener locationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
	        updateWithNewLocation(location);
	    }

	    public void onProviderDisabled(String provider){
	        updateWithNewLocation(null);
	    }

	    public void onProviderEnabled(String provider) {}
	    public void onStatusChanged(String provider, int status, Bundle extras) {}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main); 
	    // MapView의 레퍼런스를 얻어온다.
	    MapView myMapView = (MapView)findViewById(R.id.myMapView);
	    // MapView의 컨트롤러를 얻어온다.
	    mapController = myMapView.getController();

	    // 지도 표시 옵션을 구성한다.
	    myMapView.setSatellite(true);
	    myMapView.setStreetView(true);
	    myMapView.displayZoomControls(false);

	    // 지도를 확대한다.
	    mapController.setZoom(17);
	    
	    // MyPositionOverlay를 추가한다.
	    positionOverlay = new MyPositionOverlay();
	    List<Overlay> overlays = myMapView.getOverlays();
	    overlays.add(positionOverlay);

	    LocationManager locationManager;
	    String context = Context.LOCATION_SERVICE;
	    locationManager = (LocationManager)getSystemService(context);

	    Criteria criteria = new Criteria();
	    criteria.setAccuracy(Criteria.ACCURACY_FINE);
	    criteria.setAltitudeRequired(false);
	    criteria.setBearingRequired(false);
	    criteria.setCostAllowed(true);
	    criteria.setPowerRequirement(Criteria.POWER_LOW);
	    String provider = locationManager.getBestProvider(criteria, true);

	    Location location = locationManager.getLastKnownLocation(provider);

	    updateWithNewLocation(location);

	    locationManager.requestLocationUpdates(provider, 2000, 10,
	    locationListener);
	}

	private void updateWithNewLocation(Location location) {
	    String latLongString;
	    TextView myLocationText;
	    myLocationText = (TextView)findViewById(R.id.myLocationText);
	    String addressString = "주소를 찾을 수 없습니다.";

	    if (location != null) {
	    	// 내 위치 마커를 업데이트한다.
	        positionOverlay.setLocation(location);
	    	
	        // 지도 위치를 업데이트한다.
	        Double geoLat = location.getLatitude()*1E6;
	        Double geoLng = location.getLongitude()*1E6;
	        GeoPoint point = new GeoPoint(geoLat.intValue(), geoLng.intValue());

	        mapController.animateTo(point);

	        double lat = location.getLatitude();
	        double lng = location.getLongitude();
	        latLongString = "위도:" + lat + "\n경도:" + lng;

	        double latitude = location.getLatitude();
	        double longitude = location.getLongitude();

	        Geocoder gc = new Geocoder(this, Locale.getDefault());
	        try {
	            List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
	            StringBuilder sb = new StringBuilder();
	            if (addresses.size() > 0) {
	                Address address = addresses.get(0);

	                for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
	                    sb.append(address.getAddressLine(i)).append("\n");

	                sb.append(address.getLocality()).append("\n");
	                sb.append(address.getPostalCode()).append("\n");
	                sb.append(address.getCountryName());
	            }
	            addressString = sb.toString();
	        } catch (IOException e) {}
	    } else {
	        latLongString = "위치를 찾을 수 없습니다.";
	    }
	    myLocationText.setText("현재 위치:\n" + latLongString + "\n" + addressString);
	}

}