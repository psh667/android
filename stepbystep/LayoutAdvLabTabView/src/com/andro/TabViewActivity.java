package com.andro;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TabViewActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // 리소스 객체 생성    
        TabHost tabHost = getTabHost();  // 탭메뉴 액티비티 생성    
        TabHost.TabSpec spec;  // 각 탭의 메뉴와 컨텐츠를 위한 객체 선언    
        Intent intent;  // 각 탭에서 사용할 인텐트 선언    
        
        // 인텐트 생성    
        intent = new Intent().setClass(this, CustListActivity.class);    
        
        // 각 탭의 메뉴와 컨텐츠를 위한 객체 생성
        spec = tabHost.newTabSpec("custList").setIndicator("고객현황").setContent(intent);    
        tabHost.addTab(spec);    
        
        intent = new Intent().setClass(this, CustRegActivity.class);    
        spec = tabHost.newTabSpec("custReg").setIndicator("고객등록").setContent(intent);    
        tabHost.addTab(spec);    
        
        intent = new Intent().setClass(this, CustHelpActivity.class);    
        spec = tabHost.newTabSpec("custHelp").setIndicator("도움말").setContent(intent);    
        tabHost.addTab(spec); 
        
        tabHost.setCurrentTab(0);
    }
}