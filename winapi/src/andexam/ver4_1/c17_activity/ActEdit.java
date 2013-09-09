package andexam.ver4_1.c17_activity;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ActEdit extends Activity {
	EditText mEdit;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actedit);

		mEdit = (EditText)findViewById(R.id.stredit);

		Intent intent = getIntent();
		String text = intent.getStringExtra("TextIn");
		if (text != null) {
			mEdit.setText(text);
		}
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnok:
			Intent intent = new Intent();
			intent.putExtra("TextOut", mEdit.getText().toString());
			setResult(RESULT_OK,intent);
			finish();
			break;
		case R.id.btncancel:
			setResult(RESULT_CANCELED);
			finish();
			break;
		}
	}
}
