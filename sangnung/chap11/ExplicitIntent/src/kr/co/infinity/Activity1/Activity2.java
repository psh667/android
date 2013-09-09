package kr.co.infinity.Activity1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity2 extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout2);
	    Button b = (Button)findViewById(R.id.Button01);
	    b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		}); 
	}

}
