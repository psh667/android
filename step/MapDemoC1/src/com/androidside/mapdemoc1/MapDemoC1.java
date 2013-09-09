package com.androidside.mapdemoc1;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class MapDemoC1 extends MapActivity {
    MapView mapView;
    
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        
        mapView = (MapView)findViewById(R.id.map);
        
        final EditText edit = (EditText) findViewById(R.id.edit);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {            
            @Override
            public void onClick(View v) {
                goLoc(edit.getText().toString());
            }
        });
    }

    public void goLoc(String goLocName) {
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        List<Address> adds;
        
        try {
            adds = gc.getFromLocationName(goLocName, 1);

            if (adds.size() == 0) {
                Toast.makeText(this, goLocName+"을 찾을 수 없습니다!", Toast.LENGTH_LONG).show();
                return;
            }
                
            int latitude = (int) (adds.get(0).getLatitude() * 1E6);
            int longitude = (int) (adds.get(0).getLongitude() * 1E6);
            
            GeoPoint gp = new GeoPoint(latitude, longitude);
            
            mapView.getController().setZoom(16);
            mapView.getController().setCenter(gp);            
            mapView.getController().animateTo(gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }

}
