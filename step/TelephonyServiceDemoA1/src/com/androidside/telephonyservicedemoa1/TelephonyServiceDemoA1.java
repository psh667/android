package com.androidside.telephonyservicedemoa1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelephonyServiceDemoA1 extends Activity {
    int state = -1;
    TelephonyManager tm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //PhoneStateListener 객체를 TelephonyManager에 등록한다. 
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(psl, PhoneStateListener.LISTEN_SERVICE_STATE); 
        
        final TextView text = (TextView) findViewById(R.id.text);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {            
            @Override
            public void onClick(View v) {
                text.setText(getServiceState(state));
            }
        });
    }
    
    //폰 상태를 감지하는 리스너 객체 생성
    PhoneStateListener psl = new PhoneStateListener() {
        //전화 서비스 상태가 변경되었을 때 호출되는 메소드
        public void onServiceStateChanged(ServiceState serviceState) {
            state = serviceState.getState();
        }
    };
    
    
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        
        tm.listen(psl, PhoneStateListener.LISTEN_NONE);
    }
    
    //서비스 상태를 문자열로 반환한다.
    private String getServiceState(int state) {
        String serviceText = "";
                
        switch(state) {        
        case ServiceState.STATE_IN_SERVICE : 
            serviceText = "서비스 정상"; break;
        case ServiceState.STATE_OUT_OF_SERVICE : 
            serviceText = "서비스 불가"; break;
        case ServiceState.STATE_EMERGENCY_ONLY : 
            serviceText = "긴급 전화만 가능"; break;
        case ServiceState.STATE_POWER_OFF : 
            serviceText = "전화 무선 꺼져 있음"; break;
        } 
        
        return serviceText;
    }
}