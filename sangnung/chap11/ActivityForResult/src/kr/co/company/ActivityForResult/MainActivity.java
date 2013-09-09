package kr.co.company.ActivityForResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	static final int GET_STRING = 1;
	TextView text;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		// Find the button defined in the main.xml
		Button button = (Button) findViewById(R.id.button);
		text = (TextView) findViewById(R.id.text);
		// Add an OnClickListener to it, that will open the SubActivity
		button.setOnClickListener(new OnClickListener() {
			// @Override
			public void onClick(View arg0) {
				Intent i = new Intent(MainActivity.this, SubActivity.class);
				startActivityForResult(i, GET_STRING);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == GET_STRING) {
			if (resultCode == RESULT_OK) {
				text.setText(data.getStringExtra("INPUT_TEXT"));
			}
		}
	}
}