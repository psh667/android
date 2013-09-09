package org.nashorn.exam0405;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MyPreference extends PreferenceActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
	}
	
}