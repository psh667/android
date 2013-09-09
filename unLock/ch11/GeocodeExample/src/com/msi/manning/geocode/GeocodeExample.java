package com.msi.manning.geocode;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class GeocodeExample extends Activity {

    private EditText input;
    private TextView output;
    private CheckBox isAddress;
    private Button button;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        this.input = (EditText) findViewById(R.id.input);
        this.output = (TextView) findViewById(R.id.output);
        this.button = (Button) findViewById(R.id.geocode_button);
        this.isAddress = (CheckBox) findViewById(R.id.checkbox_address);

        this.button.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                output.setText(performGeocode(input.getText().toString(), isAddress.isChecked()));
            }
        });
    }

    // Note that this is not at all robust, rather it's a quick and dirty example
    // (splitting a string and then parsing as a double is a brittle approach, use more checks in
    // production code)
    private String performGeocode(final String in, final boolean isAddr) {
        String result = "Unable to Geocode - " + in;
        if (this.input != null) {
            Geocoder geocoder = new Geocoder(this);
            if (isAddr) {
                try {
                    List<Address> addresses = geocoder.getFromLocationName(in, 1);
                    if (addresses != null) {
                        result = addresses.get(0).toString();
                    }
                } catch (IOException e) {
                    Log.e("GeocodExample", "Error", e);
                }
            } else {
                try {
                    String[] coords = in.split(",");
                    if ((coords != null) && (coords.length == 2)) {
                        List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(coords[0]), Double
                            .parseDouble(coords[1]), 1);
                        result = addresses.get(0).toString();
                    }
                } catch (IOException e) {
                    Log.e("GeocodExample", "Error", e);
                }
            }
        }
        return result;
    }
}
