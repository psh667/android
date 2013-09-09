package com.appstudio.android.sample.ch_25_2;

import com.appstudio.android.sample.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferenceScreenSimpleActivy 
                                  extends PreferenceActivity  { 
    final private String TAG = "appstudio";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_nobutton);
    }
}