package com.androidside.locationdemoa1;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LocationDemoA1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TextView text = (TextView) findViewById(R.id.text);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LocationManager mgr = 
                    (LocationManager) getSystemService(LOCATION_SERVICE);
                Location loc = mgr
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                String locStr = null;

                if (loc != null) {
                    locStr = "위도 : " + loc.getLatitude() + "\n경도 : "
                            + loc.getLongitude();
                } else {
                    locStr = "위치를 찾을 수 없습니다.";
                }

                text.setText(locStr);
            }
        });
    }
}