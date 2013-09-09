package com.corea.BasicLocDemo;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class BasicLocDemo extends Activity {
    /** Called when the activity is first created. */
	
	LocationManager locManager;							// 로케이션 메니져 객체 생성// 위치정보는 모두 로케이션 메니저에서 접근, 처리한다.
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String loc_context = Context.LOCATION_SERVICE;		// 
        locManager = (LocationManager)getSystemService(loc_context);
        locationProviders();
    }
    
    public void locationProviders() {
    	TextView tv = (TextView)findViewById(R.id.TextView01);
		
    	/*
    	 1. String 객체는 불변이기 때문에 변하지 않는 문자열은 String을 사용한다. 
		 2. StringBuilder는 비동기방식이기 때문에 Single Thread 환경하에서, 변화되는 문자열의 사용한다. 
		 3. StringBuffer 동기방식으로 저장되기 때문에 멀티쓰레드로 접근하거나 문자열이 변경될 경우에 사용한다.
    	 */
    	
    	List<String> pvdGroup = locManager.getProviders(true);		// 현재 사용할수 있는 공급자를 리스트로 불러들인다.
    	
    	/*
    	public void requestLocationUpdates (String provider, long minTime, float minDistance, LocationListener listener) 
    	provider: 사용할 프로바이더의 이름
    	minTime: 몇 밀리초 간격으로 통지할것인지 정의
    	minDistance 최소 간격, 단위는 미터
    	listener 어떤 리스너가 통지되었는지 기술
    	 */
    	StringBuilder str = new StringBuilder("");
    	for(String pvder : pvdGroup) {									// 가져온 리스트의 크기만큼 반복
        	str.append("위치 공급자 ");
    		locManager.requestLocationUpdates
    		       (pvder, 2000, 0, new LocationListener() {				// 현재 사용할수있는 프로바이더를 가지고 오고, 1초 간격으로 업데이트
    		    	   public void onLocationChanged(Location location) {}		// 위치 변화감지
    		    	   public void onProviderDisabled(String provider) {}		// 프로바이더 꺼짐 감지
    		    	   public void onProviderEnabled(String provider) {}		// 프로바이더 활성 감지
    		    	   public void onStatusChanged								// 상태변화 감지
    		    	        (String provider, int status, Bundle extras) {}
    		       });
    		str.append(":").append(pvder).append("\n");						// 여기서 활성화된 위치 공급자의 이름을 하나 찍어줌. 예제에서는 GPS가 나올것임
    		
    		Location loc = locManager.getLastKnownLocation(pvder);	// 위치메니저로부터 마지막에 알려진 위치를 좌표에 넣는다.
    		if(loc != null) {												// 전에 지정된 위치가 있었다면
    			double lat = loc.getLatitude();      // 위도: 가로			// 그 위치를 받아올 것이고,
    			double lng = loc.getLongitude();     // 경도: 세로 
                        str.append("위도:").append(lat).append(", 경도:").append(lng+"\n");	// 표시해줄 것이다.
    		} else {
    			str.append("위치를 찾을 수 없음");									// 만약에 위치를 못찾았다면, 위치를 찾을 수 없다고 표시될것이다.
    		}
    		
    	}
    	tv.setText(str); 
    }
}