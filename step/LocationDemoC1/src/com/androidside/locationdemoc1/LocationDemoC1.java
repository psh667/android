package com.androidside.locationdemoc1;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LocationDemoC1 extends Activity implements View.OnClickListener {
    EditText edit;
    TextView text;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        edit = (EditText) findViewById(R.id.edit);
        text = (TextView) findViewById(R.id.text);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        
        String locationName = edit.getText().toString();
        
        int maxResults = 4;
        try {
            List<Address> addrs = geocoder.getFromLocationName(locationName, maxResults);
            
            String locations = "";
            for (Address addr : addrs) {   
                for (int i =  0; i <= addr.getMaxAddressLineIndex(); i++) {
                    locations += addr.getAddressLine(i) + "\n";
                }
                
                locations += "위도: " + addr.getLatitude() + "\n";
                locations += "경도: " + addr.getLongitude() + "\n\n";
            }
            
            text.setText(locations);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
}