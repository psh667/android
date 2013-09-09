package com.androidside.prefdemoc1;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PrefDemoC1 extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.prefs_custom);
    }
}