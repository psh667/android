package andexam.ver4_1.c04_view;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ButtonEdit extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buttonedit);

		Button btn = (Button)findViewById(R.id.btn);
		btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				EditText edit = (EditText)findViewById(R.id.edit);
				String str = edit.getText().toString();
				Toast.makeText(ButtonEdit.this, str, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
