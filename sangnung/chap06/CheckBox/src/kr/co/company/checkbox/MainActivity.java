package kr.co.company.checkbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox);
		checkbox.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 체크 박스가 클릭되면 호출된다. 체크 여부를 확인한다. 					
				if (((CheckBox) v).isChecked()) {
					Toast.makeText(getApplicationContext(), "Selected",
						Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "Not selected",
						Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
