package com.androidside.prefdemoa2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class PrefDemoA2 extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.prefs);
        
        Preference p = findPreference("mobile");
        p.setOnPreferenceClickListener(new OnPreferenceClickListener() {            
            @Override
            public boolean onPreferenceClick(Preference arg0) {
                Uri uri = Uri.parse("http://m.androidside.com");
                Intent browser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browser);
                
                return false;
            }
        });
    }
}