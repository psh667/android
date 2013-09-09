package kr.co.company.SaveRestoreTest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SaveRestoreTestActivity extends Activity {
	/** Called when the activity is first created. */
	Button button1, button2;
	TextView text;
	int count = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		text = (TextView) findViewById(R.id.text);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				count++;
				text.setText("현재 개수=" + count);
			}
		});
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				count--;
				text.setText("현재 개수=" + count);
			}
		});
		if (savedInstanceState != null) {
			count = savedInstanceState.getInt("count");
			text.setText("현재 개수=" + count);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("count", count);
	}
}