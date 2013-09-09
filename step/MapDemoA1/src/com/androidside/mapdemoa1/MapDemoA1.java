package com.androidside.mapdemoa1;

import android.os.Bundle;
import com.google.android.maps.MapActivity;

public class MapDemoA1 extends MapActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}