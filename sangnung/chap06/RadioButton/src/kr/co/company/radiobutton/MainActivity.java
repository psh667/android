package kr.co.company.radiobutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	private OnClickListener radio_listener = new OnClickListener() {
		public void onClick(View v) {
			// 클릭되면 호출된다.
			RadioButton rb = (RadioButton) v;
			Toast.makeText(getApplicationContext(), rb.getText(),
					Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final RadioButton red = (RadioButton) 
				findViewById(R.id.radio_red);
		final RadioButton blue = (RadioButton) 	
			findViewById(R.id.radio_blue);
		red.setOnClickListener(radio_listener);
		blue.setOnClickListener(radio_listener);
	}
}