package com.androidside.locationdemoa2;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class LocationDemoA2 extends Activity implements View.OnClickListener {
    LocationManager locMgr;
    LocationListener locLnr;
    
    TextView text;
    Button startButton;
    Button stopButton;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locLnr = new MyLocationListener();
        
        text = (TextView) findViewById(R.id.text);
        startButton = (Button) findViewById(R.id.start);
        stopButton = (Button) findViewById(R.id.stop);
        
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        int millis = 5000; //밀리세컨드
        int distance = 5; //거리
                
        if (v == startButton) { //위치 감지를 시작한다.
            locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
                    millis, distance, locLnr);
            text.setText("위치 감지를 시작합니다.");
        } else if (v == stopButton) { //위치 감지를 중지한다.
            locMgr.removeUpdates(locLnr);
            text.setText("위치 감지를 중지합니다.");
        }
    }
    
    //위치 정보를 감지하는 리스너
    public class MyLocationListener implements LocationListener {
        
        //위치 정보가 변경되었을 때 호출된다.
        @Override
        public void onLocationChanged(Location loc) {
            String text = "현재 위치 : \n" + "위도 : " + loc.getLatitude() + "\n"
                    + "경도 : " + loc.getLongitude();

            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
                    .show();
        }

        //위치 제공자가 변경되었을 때 호출된다.
        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "GPS 이용 불가",
                    Toast.LENGTH_SHORT).show();
        }

        //위치 제공자가 활성화되었을 때 호출된다.
        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "GPS 이용 가능",
                    Toast.LENGTH_SHORT).show();
        }

        //위치 제공자의 상태가 변경되었을 때 호출된다.
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }
}