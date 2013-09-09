package com.corea.RevGeoDemo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class RevGeoDemo extends Activity {
	
 @Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.main);

	  LocationManager locManager;														
	  locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);		// 위치매니저 객체 생성

	  Criteria reqment = new Criteria();				// 최적의 공급자를 얻기위해
	  reqment.setAccuracy(Criteria.ACCURACY_FINE);
	  reqment.setAltitudeRequired(false);
	  reqment.setBearingRequired(false);
	  reqment.setCostAllowed(true);
	  reqment.setPowerRequirement(Criteria.POWER_LOW);

	  String pvder = locManager.getBestProvider(reqment, true);	// criteria의 조건을 갖춘 공급자를 얻어온다.

	  Location loc = locManager.getLastKnownLocation(pvder);	// 공급자로부터 최근 위치를 받아온다.
	  LocUpdate(loc);
	  locManager.requestLocationUpdates(pvder, 2000, 10, locListener);		// 업데이트 되는 조건을 설정한다. 
	}
	
    private final LocationListener locListener = new LocationListener() {		// 위치변화의 리스너
       public void onLocationChanged(Location location) {
    	   LocUpdate(location);
       }
       public void onProviderDisabled(String provider){
    	   LocUpdate(null);
       }
       public void onProviderEnabled(String provider) {}
       public void onStatusChanged(String provider, int status, Bundle extras){}
    };
	
  /** Update UI with a new location */
	private void LocUpdate(Location loc) {
	  TextView LocTV = (TextView)findViewById(R.id.LocTextView);

      String str;
	  String addrStr = null;
	 	  
	  if (loc != null) {
	    double lat = loc.getLatitude();  
	    double lng = loc.getLongitude();
	    str = "(위도:" + lat + ", 경도:" + lng + ")";				// 설정된 위치의 위도 경도를 출력

	    Geocoder gCoder = new Geocoder(this, Locale.getDefault());				// geocoder 객체 생성
	    
	    try {
	      List<Address> addrList = gCoder.getFromLocation(lat, lng, 1);		// address에 위도 경도 넣는다.
	      StringBuilder strBlder = new StringBuilder();
	      if (addrList.size() > 0) {										// 위치가 있으면
	        Address addr = addrList.get(0);

	        for (int i = 0; i < addr.getMaxAddressLineIndex(); i++)
	        	strBlder.append(addr.getAddressLine(i)).append("\n");   		

	        strBlder.append(addr.getLocality()).append("\n");					// address의 위치정보를 가져와 붙인다.
	        strBlder.append(addr.getPostalCode()).append("\n");				// address의 우편번호를 가져와 붙인다.
	        strBlder.append(addr.getCountryName());							// address의 나라이름을 가져와 붙인다.
	        addrStr = strBlder.toString();
	    } else
	    	addrStr = "주소 찾을 수 없음.";
	    } catch (IOException e) {System.out.println("IO Exception occurred.\n");}
	  } else {
		  str = "위치 발견 되지 않음.";
	  }
	  LocTV.setText("좌표:\n" + str + "\n\n변환주소:\n" + addrStr);
	}
}