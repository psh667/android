package com.androidside.subway.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.androidside.subway.R;

public class Station extends TabActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub

		Intent intent = this.getIntent();
		String strStationName = (intent.getExtras()).getString("StationName");
		String strStationLine = (intent.getExtras()).getString("StationLine");
		String strCID = (intent.getExtras()).getString("StationLocal");

		Intent StationInfoIntent = new Intent(Station.this,
				Tab1_ArrivalInfo.class);
		StationInfoIntent.putExtra("StationName", strStationName);
		StationInfoIntent.putExtra("StationLine", strStationLine);
		StationInfoIntent.putExtra("StationLocal", strCID);

		TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("Arrival")
				.setIndicator("열차도착정보", getResources().getDrawable(R.drawable.subway))
				.setContent(StationInfoIntent));

		tabHost.addTab(tabHost.newTabSpec("Exit")
				.setIndicator("출구정보", getResources().getDrawable(R.drawable.exit))
				.setContent(new Intent(Station.this, Tab2_ExitInfo.class)));

		tabHost.addTab(tabHost.newTabSpec("StationInfo")
				.setIndicator("역정보", getResources().getDrawable(R.drawable.info))
				.setContent(new Intent(Station.this, Tab3_StationInfo.class)));
	}
}
