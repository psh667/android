package ex.NetworkTest02;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NetworkTest02 extends Activity {
	EditText display;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		Button get = (Button) findViewById(R.id.get);
		display = (EditText) findViewById(R.id.display);
		get.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				try {
					ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo info1 = manager
							.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
					NetworkInfo info2 = manager
							.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

					// String status = ""+info1.isAvailable() +
					// info1.isConnected()+
					// info2.isAvailable() + info2.isConnected();
					display.setText(info1.toString() + "\n\n"
							+ info2.toString());
				} catch (Exception e) {
				}
			}
		});
	}
}
