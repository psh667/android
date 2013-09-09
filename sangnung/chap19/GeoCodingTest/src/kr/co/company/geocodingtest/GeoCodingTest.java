package kr.co.company.geocodingtest;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GeoCodingTest extends Activity {
		/** Called when the activity is first created. */
		Geocoder coder;
		TextView result;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			coder = new Geocoder(this);
			result = ((TextView) findViewById(R.id.result));
		}

		public void onClick(View v) {
			List<Address> list = null;
			String lattitude = ((EditText) findViewById(R.id.lattitude)).getText()
					.toString();
			String longitude = ((EditText) findViewById(R.id.longitude)).getText()
					.toString();
			try {
				list = coder.getFromLocation(Double.parseDouble(lattitude),
						Double.parseDouble(longitude), 10);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				result.setText("입출력 오류: "+ e.getMessage());
				e.printStackTrace();
			}
			if (list != null)
				result.setText(list.get(0).toString());
		}

		public void onClick1(View v) {
			List<Address> list = null;
			String address = ((EditText) findViewById(R.id.address)).getText()
					.toString();
			try {
				list = coder.getFromLocationName(address, 10);
			} catch (IOException e) {
				result.setText("입출력 오류: "+ e.getMessage());
				e.printStackTrace();
			}
			if (list != null)
				result.setText(list.get(0).toString());
		}
	}

