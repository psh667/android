package andexam.ver4_1.c05_layout;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Horizontal1 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.horizontal1);

		Button btn = (Button)findViewById(R.id.btn);
		btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				EditText edit = (EditText)findViewById(R.id.edit);
				String str = edit.getText().toString();
				Toast.makeText(Horizontal1.this, str, 
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}