package com.msi.manning.windwaves;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.msi.manning.windwaves.data.BuoyData;

// ENHANCE build charts from the realtime data (http://www.ndbc.noaa.gov/data/realtime2/41002.txt)
// ENHANCE hook in with other NOAA data - http://www.weather.gov/rss/

/**
 * BuoyDetail Activity for WindWaves.
 * 
 * @author charliecollins
 */
public class BuoyDetailActivity extends Activity {

    private static final String CLASSTAG = BuoyDetailActivity.class.getSimpleName();

    public static final String BUOY_DETAIL_URL = "http://www.ndbc.noaa.gov/station_page.php?station=";

    public static BuoyData buoyData = null;

    private Button buttonWeb;
    private TextView title;
    private TextView location;
    private TextView date;
    private TextView airTemp;
    private TextView waterTemp;
    private TextView atmoPress;
    private TextView atmoTend;
    private TextView windDir;
    private TextView windSpeed;
    private TextView windGust;
    private TextView waveHeight;
    private TextView wavePeriod;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        Log.v(Constants.LOGTAG, " " + BuoyDetailActivity.CLASSTAG + " onCreate");

        this.setContentView(R.layout.buoydetail_activity);

        this.buttonWeb = (Button) findViewById(R.id.button_web);
        this.title = (TextView) findViewById(R.id.bd_title);
        this.location = (TextView) findViewById(R.id.bd_location);
        this.date = (TextView) findViewById(R.id.bd_date);
        this.airTemp = (TextView) findViewById(R.id.bd_air_temp);
        this.waterTemp = (TextView) findViewById(R.id.bd_water_temp);
        this.atmoPress = (TextView) findViewById(R.id.bd_atmo_press);
        this.atmoTend = (TextView) findViewById(R.id.bd_atmo_tend);
        this.windDir = (TextView) findViewById(R.id.bd_wind_dir);
        this.windSpeed = (TextView) findViewById(R.id.bd_wind_speed);
        this.windGust = (TextView) findViewById(R.id.bd_wind_gust);
        this.waveHeight = (TextView) findViewById(R.id.bd_wave_height);
        this.wavePeriod = (TextView) findViewById(R.id.bd_wave_period);

        this.title.setText(BuoyDetailActivity.buoyData.title);
        this.location.setText("Location:" + BuoyDetailActivity.buoyData.location);
        this.date.setText("Data Poll Date: "
            + (BuoyDetailActivity.buoyData.dateString != null ? BuoyDetailActivity.buoyData.dateString : "NA"));
        this.airTemp.setText("Air Temp: "
            + (BuoyDetailActivity.buoyData.airTemp != null ? BuoyDetailActivity.buoyData.airTemp : "NA"));
        this.waterTemp.setText("Water Temp: "
            + (BuoyDetailActivity.buoyData.waterTemp != null ? BuoyDetailActivity.buoyData.waterTemp : "NA"));
        this.atmoPress.setText("Barometric Press: "
            + (BuoyDetailActivity.buoyData.atmoPressure != null ? BuoyDetailActivity.buoyData.atmoPressure : "NA"));
        this.atmoTend
            .setText("Barometric Trend: "
                + (BuoyDetailActivity.buoyData.atmoPressureTendency != null ? BuoyDetailActivity.buoyData.atmoPressureTendency
                    : "NA"));
        this.windDir.setText("Wind Direction: "
            + (BuoyDetailActivity.buoyData.windDirection != null ? BuoyDetailActivity.buoyData.windDirection : "NA"));
        this.windSpeed.setText("Wind Speed: "
            + (BuoyDetailActivity.buoyData.windSpeed != null ? BuoyDetailActivity.buoyData.windSpeed : "NA"));
        this.windGust.setText("Wind Gust: "
            + (BuoyDetailActivity.buoyData.windGust != null ? BuoyDetailActivity.buoyData.windGust : "NA"));
        this.waveHeight.setText("Wave Height: "
            + (BuoyDetailActivity.buoyData.waveHeight != null ? BuoyDetailActivity.buoyData.waveHeight : "NA"));
        this.wavePeriod.setText("Wave Period: "
            + (BuoyDetailActivity.buoyData.wavePeriod != null ? BuoyDetailActivity.buoyData.wavePeriod : "NA"));

        this.buttonWeb.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(BuoyDetailActivity.buoyData.link)));
            };
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(Constants.LOGTAG, " " + BuoyDetailActivity.CLASSTAG + " onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
