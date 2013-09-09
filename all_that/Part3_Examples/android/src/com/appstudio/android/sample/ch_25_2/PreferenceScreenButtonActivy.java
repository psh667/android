package com.appstudio.android.sample.ch_25_2;

import com.appstudio.android.sample.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;

public class PreferenceScreenButtonActivy 
                                  extends PreferenceActivity  {
    final private String TAG = "appstudio";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference button = 
            (Preference)findPreference("button");
        button.setOnPreferenceClickListener(
                   new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {
                Intent intent = 
                   new Intent(PreferenceScreenButtonActivy.this
                      , PreferenceMenualEditingActivity.class);
                
                startActivityForResult(intent,0);
                return true;}}
        );	  
    }
}