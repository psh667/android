package com.androidside.mapdemod1;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class MapDemoD1 extends MapActivity implements View.OnClickListener {
    LocationManager locMgr;
    LocationListener locLnr;

    MapView map;
    Geocoder geocoder;

    Button startButton;
    Button stopButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locLnr = new MyLocationListener();

        map = (MapView) findViewById(R.id.map);
        map.getController().setZoom(map.getMaxZoomLevel());
        map.setBuiltInZoomControls(true);

        geocoder = new Geocoder(this, Locale.KOREA);

        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(this);

        stopButton = (Button) findViewById(R.id.stop);
        stopButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int millis = 5000; // 밀리세컨드
        int distance = 5; // 거리(미터)

        if (v == startButton) {
            // 위치 감지 시작
            locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, millis,
                    distance, locLnr);
        } else if (v == stopButton) {
            // 위치 감지 중지
            locMgr.removeUpdates(locLnr);
        }
    }

    public class MyLocationListener implements LocationListener {
        @Override
        // 위치가 변경되었을 때 호출
        public void onLocationChanged(Location loc) {
            GeoPoint gp = new GeoPoint((int) (loc.getLatitude() * 1E6),
                    (int) (loc.getLongitude() * 1E6));

            map.getController().animateTo(gp);

            Toast.makeText(getApplicationContext(), "주소 : " + getAddress(loc),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        // 위치 제공자가 비활성화되었을 때 호출
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "GPS 이용 불가",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        // 위치 제공자가 활성화되었을 때 호출
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "GPS 이용 가능",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        // 상태가 변경되었을 때 호출
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // 주소를 반환한다.
        private String getAddress(Location loc) {
            StringBuffer sb = new StringBuffer();

            try {
                List<Address> addrs = geocoder.getFromLocation(loc
                        .getLatitude(), loc.getLongitude(), 1);

                for (Address addr : addrs) {
                    for (int i = 0; i <= addr.getMaxAddressLineIndex(); i++) {
                        sb.append(addr.getAddressLine(i));
                        sb.append(" ");
                    }
                }
            } catch (Exception e) {
                sb.append("주소지를 얻다가 에러가 발생했습니다.");
            }

            return sb.toString();
        }
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}