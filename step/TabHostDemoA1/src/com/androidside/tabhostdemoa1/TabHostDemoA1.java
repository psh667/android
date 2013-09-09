package com.androidside.tabhostdemoa1;

import android.app.TabActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabHostDemoA1 extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost();
        
        TabSpec tabSpec1 = tabHost.newTabSpec("Tab1").setIndicator("Ã¹ ¹øÂ° ÅÇ");        
        tabSpec1.setContent(R.id.tab1);
        tabHost.addTab(tabSpec1);
        
        TabSpec tabSpec2 = tabHost.newTabSpec("Tab2").setIndicator("µÎ ¹øÂ° ÅÇ");
        tabSpec2.setContent(R.id.tab2);
        tabHost.addTab(tabSpec2);
        
        Drawable img = getResources().getDrawable(R.drawable.star);
        TabSpec tabSpec3 = tabHost.newTabSpec("Tab3").setIndicator("¼¼ ¹øÂ° ÅÇ", img);
        tabSpec3.setContent(R.id.tab3);
        tabHost.addTab(tabSpec3);
        
        tabHost.setCurrentTab(0);
    }
}