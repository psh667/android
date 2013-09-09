package com.androidside.locationdemoc2;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LocationDemoC2 extends Activity implements View.OnClickListener {
    double latitude = 37.5115715;
    double longitude = 127.1002647;
    
    TextView addrText;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
        
        addrText = (TextView) findViewById(R.id.address);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        
        int maxResults = 4;
        try {
            List<Address> addrs = geocoder.getFromLocation(latitude, longitude, maxResults);
            
            String locations = "";
            for (Address addr : addrs) {   
                for (int i =  0; i <= addr.getMaxAddressLineIndex(); i++) {
                    locations += addr.getAddressLine(i) + "\n";
                }
                
                locations += addr.getPostalCode() + "\n";
                
                locations += "위도: " + addr.getLatitude() + "\n";
                locations += "경도: " + addr.getLongitude() + "\n\n";
            }
            
            addrText.setText(locations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}