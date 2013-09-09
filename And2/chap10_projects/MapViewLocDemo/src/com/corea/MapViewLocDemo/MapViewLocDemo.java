package com.corea.MapViewLocDemo;

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

public class MapViewLocDemo extends MapActivity {		
	
	MapController mapControl;										// google map을 다루는 클래스, google API에 담겨있다.

	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.main);

	  MapView mapView = (MapView)findViewById(R.id.MapView01);		
	  mapControl = mapView.getController();						// mapView를 다룰 수 있게 하기 위해 컨트롤러 객체를 생성한다.
	  
	  // Configure the map display options
	  mapView.setSatellite(true);										// 위성사진으로 보여준다.
	  mapView.setStreetView(true);									// 거리가 표시된다.
	  mapView.displayZoomControls(false);								// 줌 컨트롤 기능을 off시킨다.
	  
	  // Zoom in
	  mapControl.setZoom(17);										// 지도의 줌을 17로 세팅한다.

	  LocationManager locManager = 
                  (LocationManager)getSystemService(Context.LOCATION_SERVICE);	// 위치 매니저 객체 생성한다.

	  Criteria reqment = new Criteria();										// 최적의 공급자를 설정하기 위한 criteria를 설정한다.
	  reqment.setAccuracy(Criteria.ACCURACY_FINE);								// 
	  reqment.setAltitudeRequired(false);
	  reqment.setBearingRequired(false);
	  reqment.setCostAllowed(true);
	  reqment.setPowerRequirement(Criteria.POWER_LOW);
	  String provider = locManager.getBestProvider(reqment, true);		// 공급자를 찾는다.

	  Location location = locManager.getLastKnownLocation(provider);		// 마지막에 설정된 위치를 location에 담는다.
      try {
    	  locUpdate(location);
      } catch (NullPointerException e) {
    	  // DDMS에서 좌표값을 넣지 않으면 널값을 반환한다.
      }
	  locManager.requestLocationUpdates(provider, 2000, 10, locationListener);
	}

	private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
        	locUpdate(location);  
        }
        public void onProviderDisabled(String provider){     
        	locUpdate(null);
        }
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

  /** Update the map with a new location */
	private void locUpdate(Location location) {
	  // Update the map location.
	  
	     Double geoLat = location.getLatitude()*1E6;									// 위치 좌표 설정 GeoPoing 객체는 위도, 경도 표시에 1E6를 곱해줘야한다.
	     Double geoLng = location.getLongitude()*1E6;		
	     GeoPoint gPoint = new GeoPoint(geoLat.intValue(), geoLng.intValue());			// 위치 지정

	     mapControl.animateTo(gPoint);
	  
	     TextView locText = (TextView)findViewById(R.id.LocText);
		  
	     String latLngStr;
	 	 String addrStr = "No address found";

	 	  if (location != null) {
	 	    double lat = location.getLatitude();
	 	    double lng = location.getLongitude();
	 	    latLngStr = "위도:" + lat + "\n경도:" + lng;

	 	    Geocoder gCode = new Geocoder(this, Locale.getDefault());
	 	    try {
	 	      List<Address> addresses = gCode.getFromLocation(lat, lng, 1);
	 	      StringBuilder strblder = new StringBuilder();
	 	      if (addresses.size() > 0) {
	 	        Address address = addresses.get(0);

	 	        for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
	 	        	strblder.append(address.getAddressLine(i)).append("\n");

	 	       strblder.append(address.getLocality()).append("\n");
	 	       strblder.append(address.getPostalCode()).append("\n");
	 	       strblder.append(address.getCountryName());
	 	      }
	 	      addrStr = strblder.toString();
	 	    } catch (IOException e) {}
	 	  } else {
	 		 latLngStr = "위치 찾지 못했음.";
	 	  }

	 	  locText.setText("당신의 현재 위치\n" + latLngStr + "\n\n당신의 주소:\n" + addrStr);
    }

    @Override
    protected boolean isRouteDisplayed() {
          return false;
    }
}
