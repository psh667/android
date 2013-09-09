package org.nashorn.exam0608;

import android.os.Bundle;
import com.google.android.maps.MapActivity;

public class MapViewActivity extends MapActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}