package andexam.ver4_1.c11_widget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ButtonSelector extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buttonselector);

		Button btn = (Button)findViewById(R.id.btnexit);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(ButtonSelector.this, "Exit Button Clicked", 
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}