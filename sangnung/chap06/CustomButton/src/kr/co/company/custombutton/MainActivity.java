package kr.co.company.custombutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Beep Bop",
						Toast.LENGTH_SHORT).show();
			}
		});

	}
}