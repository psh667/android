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

public class PreferenceScreenActivity extends PreferenceActivity 
                 implements OnSharedPreferenceChangeListener  {
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
                    new Intent(PreferenceScreenActivity.this
                      , PreferenceMenualEditingActivity.class);
                
                startActivityForResult(intent,0);
                return true;}}
        );	  
    }

    @Override 
    protected void onResume(){
        super.onResume();
        for(int i=0;
            i<getPreferenceScreen().getPreferenceCount();i++){
            initSummary(
                    getPreferenceScreen().getPreference(i));
        }	    
        PreferenceManager.getDefaultSharedPreferences(this)
               .registerOnSharedPreferenceChangeListener(this);
    }

    private void initSummary(Preference p){
        if (p instanceof PreferenceCategory){
            PreferenceCategory pCat = (PreferenceCategory)p;
            for(int i=0;i<pCat.getPreferenceCount();i++){
                initSummary(pCat.getPreference(i));
            }
        }else{
            updatePrefSummary(p);
        }
    }

    private void updatePrefSummary(Preference p){
        if (p instanceof ListPreference) {
            ListPreference listPref = (ListPreference) p; 
            p.setSummary(listPref.getEntry()); 
        }
        if (p instanceof EditTextPreference) {
            EditTextPreference editTextPref = 
                (EditTextPreference) p; 
            p.setSummary(editTextPref.getText()); 
        }
    }

    @Override
    public void onSharedPreferenceChanged(
            SharedPreferences sharedPreferences, String key) {
        updatePrefSummary(findPreference(key));
    }
}