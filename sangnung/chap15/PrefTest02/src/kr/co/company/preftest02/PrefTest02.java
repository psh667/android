package kr.co.company.preftest02;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PrefTest02 extends PreferenceActivity {

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.layout.pref_activity);
	    }
    
}
