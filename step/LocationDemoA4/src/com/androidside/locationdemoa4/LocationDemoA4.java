package com.androidside.locationdemoa4;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class LocationDemoA4 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //위치 관리자 구하기
        LocationManager locMgr = 
            (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String result = "";
        
        result = "위치 제공자 종류\n" + getProviders(locMgr);
        result += "\n";
        result += "최적의 위치 제공자\n" + getBestProvider(locMgr);
        
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(result);
    }
    
    //사용할 수 있는 모든 위치 제공자 구하기
    private String getProviders(LocationManager locMgr) {
        List<String> providers = locMgr.getProviders(false);
        
        String str = "";
        
        for(String provider : providers) {
            str += provider+"\n";
        }
        
        return str;
    }
    
    //기준(criteria)에 맞는 최적의 위치 제공자 구하기
    private String getBestProvider(LocationManager locMgr) {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);
        
        String provider = locMgr.getBestProvider(criteria, true);
        
        return provider;
    }
}