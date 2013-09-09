package com.androidside.prefdemoa1;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PrefDemoA1 extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.prefs);
    }
}