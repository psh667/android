package kr.co.company.googlemapstest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class GoogleMapsTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		Uri uri = Uri.parse(String.format("geo:%f,%f?z=10", 37.30, 127.2));
		startActivity( new Intent(Intent.ACTION_VIEW, uri));
    }
}
