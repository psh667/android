package com.corea.MapLocDemo;

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

public class MapLocDemo extends MapActivity {
	
	MapController mapController;

	@Override
	public void onCreate(Bundle icicle) {
	  super.onCreate(icicle);
	  setContentView(R.layout.main);

	  MapView myMapView = (MapView)findViewById(R.id.myMapView);
	  mapController = myMapView.getController();
	  
	  // Configure the map display options
	  myMapView.setSatellite(true);
	  myMapView.setStreetView(true);
	  myMapView.displayZoomControls(false);
	  
	  // Zoom in
	  mapController.setZoom(17);

	  LocationManager locationManager = 
                  (LocationManager)getSystemService(Context.LOCATION_SERVICE);

	  Criteria criteria = new Criteria();
	  criteria.setAccuracy(Criteria.ACCURACY_FINE);
	  criteria.setAltitudeRequired(false);
	  criteria.setBearingRequired(false);
	  criteria.setCostAllowed(true);
	  criteria.setPowerRequirement(Criteria.POWER_LOW);
	  String provider = locationManager.getBestProvider(criteria, true);

	  Location location = locationManager.getLastKnownLocation(provider);
	  
	  try{		
		  updateWithNewLocation(location);
	  } catch (NullPointerException e) {
		  // DDMS에서 좌표를 넣고 시작하지 않으면 널값을 가져오게 된다.
	  }

	  locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
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

  /** Update the map with a new location */
	private void updateWithNewLocation(Location location) {
	  // Update the map location.
	  
	     Double geoLat = location.getLatitude()*1E6;
	     Double geoLng = location.getLongitude()*1E6;
	     GeoPoint point = new GeoPoint(geoLat.intValue(), geoLng.intValue());

	     mapController.animateTo(point);
	  
	     TextView myLocationText = (TextView)findViewById(R.id.myLocationText);
		  
	     String latLongString;
	 	 String addressString = "No address found";

	 	  if (location != null) {
	 	    double lat = location.getLatitude();
	 	    double lng = location.getLongitude();
	 	    latLongString = "위도:" + lat + "\n경도:" + lng;

	 	    Geocoder gc = new Geocoder(this, Locale.getDefault());
	 	    try {
	 	      List<Address> addresses = gc.getFromLocation(lat, lng, 1);
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
	 	    latLongString = "위치 찾지 못했음.";
	 	  }

	 	  myLocationText.setText("당신의 현재 위치\n" + latLongString + "\n\n당신의 주소:\n" + addressString);
    }

    @Override
    protected boolean isRouteDisplayed() {
          return false;
    }
}