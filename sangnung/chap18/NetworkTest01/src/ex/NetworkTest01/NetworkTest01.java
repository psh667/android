package ex.NetworkTest01;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NetworkTest01 extends Activity {
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
					URL text = new URL("http://www.yahoo.co.kr");
					InputStream is = text.openStream();
					byte[] buffer = new byte[1000];
					is.read(buffer);
					display.setText(new String(buffer));
					is.close();
				} catch (Exception e) {
				}
			}
		});
	}
}
