package com.androidside.locationdemob1;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class LocationDemoB1 extends Activity {
    String PROXIMITY_ALERT = "com.androidside.intent.action.PROXIMITY_ALERT";
    
    ProximityAlertReceiver alertReceiver; //근접 경보 리시버
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //근접 경보 등록
        this.addProximityAlert();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        //근접 경보 리시버 등록
        alertReceiver = new ProximityAlertReceiver();
        IntentFilter filter = new IntentFilter(PROXIMITY_ALERT);
        registerReceiver(alertReceiver, filter);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        
        //근접 경보 리시버 해제
        unregisterReceiver(alertReceiver);
    }    
    
    //근접 경고 등록
    private void addProximityAlert() {
        LocationManager locMgr = 
            (LocationManager) getSystemService(LOCATION_SERVICE);
                
        Intent intent = new Intent(PROXIMITY_ALERT);
        PendingIntent pendingIntent = 
            PendingIntent.getBroadcast(this, -1, intent, 0);
        
        double latitude = 37.484241; //신림역 위도
        double longitude = 126.929651; //신림역 경도
        float radius = 10f; //10미터
        long expiration = 60*60*1000; //1시간
        
        locMgr.addProximityAlert(latitude, longitude, 
                radius, expiration, pendingIntent);
    }
}

//근접 경고 리시버
class ProximityAlertReceiver extends BroadcastReceiver {
    String key = LocationManager.KEY_PROXIMITY_ENTERING;

    @Override
    public void onReceive(Context context, Intent intent) {
        //지정된 반경 안에 들어오면 true, 그렇지 않으면 false
        boolean isEnter = intent.getBooleanExtra(key, false);
        
        if (isEnter) {
            Toast.makeText(context, "반경 안에 들어왔습니다.", Toast.LENGTH_LONG)
                .show();
        } else {
            Toast.makeText(context, "반경을 벗어났습니다.", Toast.LENGTH_LONG)
                .show();
        }
    }
    
}